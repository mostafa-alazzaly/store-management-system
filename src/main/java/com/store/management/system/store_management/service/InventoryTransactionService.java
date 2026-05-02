package com.store.management.system.store_management.service;

import com.store.management.system.store_management.dto.InventoryTransactionItem.request.CreateInventoryTransactionItemRequest;
import com.store.management.system.store_management.dto.InventoryTransactionItem.response.InventoryTransactionItemResponse;
import com.store.management.system.store_management.dto.inventoryTransaction.request.CreateInventoryTransactionRequest;
import com.store.management.system.store_management.dto.inventoryTransaction.response.InventoryTransactionResponse;
import com.store.management.system.store_management.entity.*;
import com.store.management.system.store_management.exception.*;
import com.store.management.system.store_management.repo.EmployeeRepo;
import com.store.management.system.store_management.repo.InventoryTransactionRepo;
import com.store.management.system.store_management.repo.ProductRepo;
import com.store.management.system.store_management.repo.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class InventoryTransactionService {

    private final InventoryTransactionRepo  inventoryTransactionRepo;
    private final  EmployeeRepo employeeRepo;
    private final  SupplierRepo supplierRepo;
    private final ProductRepo productRepo;

    @Autowired
    public InventoryTransactionService(InventoryTransactionRepo inventoryTransactionRepo, EmployeeRepo employeeRepo, SupplierRepo supplierRepo, ProductRepo productRepo) {
        this.inventoryTransactionRepo = inventoryTransactionRepo;
        this.employeeRepo = employeeRepo;
        this.supplierRepo = supplierRepo;
        this.productRepo = productRepo;
    }

    private InventoryTransactionResponse mapToResponse (InventoryTransaction invTran){
        InventoryTransactionResponse response = new InventoryTransactionResponse();
        response.setId(invTran.getId());
        response.setEmployeeId(invTran.getEmployee().getId());
        response.setSupplierId(
                invTran.getSupplier() != null ? invTran.getSupplier().getId() : null);
        response.setTransactionType(invTran.getTransactionType());
        response.setTransactionDate(invTran.getTransactionDate());
        response.setNotes(invTran.getNotes());
        response.setCreatedAt(invTran.getCreatedAt());
        response.setUpdatedAt(invTran.getUpdatedAt());

        List<InventoryTransactionItemResponse> itemResponses =
                invTran.getInventoryTransactionItems()
                        .stream()
                        .map(item -> {
                            InventoryTransactionItemResponse dto = new InventoryTransactionItemResponse();
                            dto.setId(item.getId());
                            dto.setInventoryTransactionId(invTran.getId());
                            dto.setProductId(item.getProduct().getId());
                            dto.setQuantity(item.getQuantity());
                            dto.setUnitCost(item.getUnitCost());
                            return dto;
                        })
                        .toList();

        response.setItems(itemResponses);
        return response;
    }

    public Page<InventoryTransactionResponse> getAllInventoryTransactions(Pageable pageable) {
        return inventoryTransactionRepo.findAll(pageable)
                .map(this::mapToResponse);
    }

    public InventoryTransactionResponse getInventoryTransactionById(Integer id){
        InventoryTransaction inventoryTransaction = inventoryTransactionRepo.findById(id)
                .orElseThrow(()-> new InventoryTransactionNotFoundException("Inventory Transaction Not Found with id : " + id ));
        return mapToResponse(inventoryTransaction);
    }

    private int getStockChangerMultiplier(InventoryTransaction.TransactionType type){
        return switch (type){
            case IN,CUSTOMER_RETURN,ADJUSTMENT -> 1;
            case OUT,SUPPLIER_RETURN,DAMAGE -> -1;
        };
    }


    @Transactional
    public InventoryTransactionResponse createInventoryTransaction(CreateInventoryTransactionRequest request) {
        InventoryTransaction inventoryTransaction = new InventoryTransaction();

        Employee employee = employeeRepo.findById(request.getEmployeeId())
                        .orElseThrow (()-> new EmployeeNotFoundException("Employee Not Found with id : " + request.getEmployeeId()));
        inventoryTransaction.setEmployee(employee);

        if (InventoryTransaction.TransactionType.IN == request.getTransactionType()
                || InventoryTransaction.TransactionType.SUPPLIER_RETURN ==  request.getTransactionType()) {

            if(request.getSupplierId() == null){
                throw new SupplierIdRequiredForInTransactionException("Supplier Id is Required");
            }

            Supplier supplier = supplierRepo.findById(request.getSupplierId())
                    .orElseThrow (()-> new SupplierNotFoundException("Supplier Not Found with id : " + request.getSupplierId()));
            inventoryTransaction.setSupplier(supplier);

        }else {
            if (request.getSupplierId() != null) {
                throw new SupplierIdOnlyRequiredForInOrSupplierReturnException("Supplier Id is Only Required For In or Supplier Return  Transaction");
            }
        }

        inventoryTransaction.setTransactionType(request.getTransactionType());
        inventoryTransaction.setTransactionDate(request.getTransactionDate());
        inventoryTransaction.setNotes(request.getNotes());

        List<String> warnings = new ArrayList<>();

        for (CreateInventoryTransactionItemRequest itemRequest : request.getItems()) {
            Product product = productRepo.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product Not Found with id : " + itemRequest.getProductId()));

            Integer quantity = itemRequest.getQuantity();

            if(request.getTransactionType() != InventoryTransaction.TransactionType.ADJUSTMENT &&
                quantity <= 0 ){
                throw new QuantityMustBePositiveException("Quantity Must Be Positive");
            }
            if(request.getTransactionType() ==  InventoryTransaction.TransactionType.ADJUSTMENT && quantity == 0){
                throw new AdjustmentQuantityCannotBeZeroException("Adjustment Quantity Can't Be Zero");
            }
            int newStockQuantity = product.getStockQuantity() + (quantity * getStockChangerMultiplier(request.getTransactionType()));

            if (newStockQuantity < 0) {
                throw new NotEnoughStockQuantityException ("Not Enough Stock Quantity");
            }
            if (newStockQuantity < product.getMinStockLevel()) {

                warnings.add(
                        "Product id " + product.getId()
                                + " is below minimum stock level. Current stock: "
                                + newStockQuantity
                                + ", minimum level: "
                                + product.getMinStockLevel());
            }
            product.setStockQuantity(newStockQuantity);

            InventoryTransactionItem item = new InventoryTransactionItem();

            item.setProduct(product);
            item.setQuantity(quantity);
            item.setUnitCost(itemRequest.getUnitCost());

            inventoryTransaction.addInventoryTransactionItem(item);

        }
        InventoryTransaction savedTransaction = inventoryTransactionRepo.save(inventoryTransaction);
        InventoryTransactionResponse response = mapToResponse(savedTransaction);
        response.setMessage("Inventory transaction created successfully");
        response.setWarnings(warnings);
        return response;
    }

    private InventoryTransaction.TransactionType getReverseTransactionType(InventoryTransaction.TransactionType type){
       return switch (type){

           case IN ,CUSTOMER_RETURN-> InventoryTransaction.TransactionType.OUT;
           case OUT, DAMAGE ,SUPPLIER_RETURN-> InventoryTransaction.TransactionType.IN;
           case ADJUSTMENT  -> InventoryTransaction.TransactionType.ADJUSTMENT ;
        };
    }

    @Transactional
    public InventoryTransactionResponse reverseInventoryTransaction(Integer transactionId) {

        InventoryTransaction originalInventoryTransaction =
                inventoryTransactionRepo.findById(transactionId)
                        .orElseThrow(() -> new InventoryTransactionNotFoundException(
                                "Inventory Transaction Not Found with id : " + transactionId
                        ));

        boolean alreadyReversed = inventoryTransactionRepo.existsByOriginalTransactionId(transactionId);

        if(alreadyReversed){
            throw new InventoryTransactionAlreadyReversedException("Inventory Transaction Already Reversed with id : " + transactionId);
        }

        if (originalInventoryTransaction.getOriginalTransaction() != null) {
            throw new CannotReverseReversedTransactionException("Cannot reverse a reversed transaction");
        }

        InventoryTransaction reverseInventoryTransaction = new InventoryTransaction();

        InventoryTransaction.TransactionType reverseType =
                getReverseTransactionType(originalInventoryTransaction.getTransactionType());

        reverseInventoryTransaction.setEmployee(originalInventoryTransaction.getEmployee());
        reverseInventoryTransaction.setSupplier(originalInventoryTransaction.getSupplier());
        reverseInventoryTransaction.setTransactionType(reverseType);
        reverseInventoryTransaction.setTransactionDate(LocalDate.now());
        reverseInventoryTransaction.setNotes(
                "Reverse transaction for transaction id : " + originalInventoryTransaction.getId()
        );
        reverseInventoryTransaction.setOriginalTransaction(originalInventoryTransaction);

        for (InventoryTransactionItem originalItem :
                originalInventoryTransaction.getInventoryTransactionItems()) {

            Product product = originalItem.getProduct();

            Integer reverseQuantity = originalItem.getQuantity();

            if (originalInventoryTransaction.getTransactionType()
                    == InventoryTransaction.TransactionType.ADJUSTMENT) {
                reverseQuantity = -reverseQuantity;
            }

            int newStockQuantity =
                    product.getStockQuantity()
                            + (reverseQuantity * getStockChangerMultiplier(reverseType));

            if (newStockQuantity < 0) {
                throw new NotEnoughStockQuantityException("Not Enough Stock Quantity");
            }

            product.setStockQuantity(newStockQuantity);
            productRepo.save(product);

            InventoryTransactionItem reverseItem = new InventoryTransactionItem();
            reverseItem.setProduct(product);
            reverseItem.setQuantity(reverseQuantity);
            reverseItem.setUnitCost(originalItem.getUnitCost());

            reverseInventoryTransaction.addInventoryTransactionItem(reverseItem);
        }

        InventoryTransaction savedReverse =
                inventoryTransactionRepo.save(reverseInventoryTransaction);

        InventoryTransactionResponse response = mapToResponse(savedReverse);
        response.setMessage("Inventory transaction reversed successfully");

        return response;
    }

}

