package com.store.management.system.store_management.service;

import com.store.management.system.store_management.dto.InventoryTransactionItem.request.CreateInventoryTransactionItemRequest;
import com.store.management.system.store_management.dto.inventoryTransaction.request.CreateInventoryTransactionRequest;
import com.store.management.system.store_management.dto.invoice.request.CreateInvoiceRequest;
import com.store.management.system.store_management.dto.invoice.request.UpdateInvoiceRequest;
import com.store.management.system.store_management.dto.invoice.response.InvoiceResponse;
import com.store.management.system.store_management.dto.invoiceItem.request.CreateInvoiceItemRequest;
import com.store.management.system.store_management.dto.invoiceItem.request.UpdateInvoiceItemRequest;
import com.store.management.system.store_management.dto.invoiceItem.response.InvoiceItemResponse;
import com.store.management.system.store_management.entity.*;
import com.store.management.system.store_management.exception.*;
import com.store.management.system.store_management.repo.*;
import com.store.management.system.store_management.specification.InvoiceSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepo invoiceRepo;
    private final EmployeeRepo employeeRepo ;
    private final CustomerRepo customerRepo;
    private final SupplierRepo supplierRepo;
    private final ProductRepo productRepo;
    private final InventoryTransactionService inventoryTransactionService;
    private final InvoiceItemRepo invoiceItemRepo;

    public InvoiceService(InvoiceRepo invoiceRepo , EmployeeRepo employeeRepo,  CustomerRepo customerRepo ,
                          SupplierRepo supplierRepo , ProductRepo productRepo,
                          InventoryTransactionService inventoryTransactionService,
                          InvoiceItemRepo invoiceItemRepo) {
        this.invoiceRepo = invoiceRepo;
        this.employeeRepo = employeeRepo;
        this.customerRepo = customerRepo;
        this.supplierRepo = supplierRepo;
        this.productRepo = productRepo;
        this.inventoryTransactionService = inventoryTransactionService;
        this.invoiceItemRepo = invoiceItemRepo;
    }


    private InvoiceResponse mapToInvoiceResponse(Invoice invoice) {
        InvoiceResponse invoiceResponse = new InvoiceResponse();

        invoiceResponse.setId(invoice.getId());
        invoiceResponse.setEmployeeId(invoice.getEmployee().getId());
        invoiceResponse.setSupplierId(invoice.getSupplier() != null ? invoice.getSupplier().getId() : null );
        invoiceResponse.setCustomerId(invoice.getCustomer() != null ? invoice.getCustomer().getId() : null );
        invoiceResponse.setInvoiceCode(invoice.getInvoiceCode());
        invoiceResponse.setInvoiceDate(invoice.getInvoiceDate());
        invoiceResponse.setDueDate(invoice.getDueDate());
        invoiceResponse.setTotalAmount(invoice.getTotalAmount());
        invoiceResponse.setDiscountAmount(invoice.getDiscountAmount());
        invoiceResponse.setFinalAmount(invoice.getFinalAmount());
        invoiceResponse.setPaidAmount(invoice.getPaidAmount());
        invoiceResponse.setRemainingAmount(invoice.getRemainingAmount());
        invoiceResponse.setInvoiceType(invoice.getInvoiceType());
        invoiceResponse.setPaymentStatus(invoice.getPaymentStatus());
        invoiceResponse.setInvoiceStatus(invoice.getInvoiceStatus());
        invoiceResponse.setNotes(invoice.getNotes());

        List<InvoiceItemResponse> itemResponse = invoice.getInvoiceItems()
                .stream()
                .map(item-> {
                        InvoiceItemResponse dto = new  InvoiceItemResponse();
                        dto.setId(item.getId());
                        dto.setInvoiceId(item.getInvoice().getId());
                        dto.setProductId(item.getProduct().getId());
                        dto.setQuantity(item.getQuantity());
                        dto.setUnitPrice(item.getUnitPrice());
                        dto.setDiscount(item.getDiscount());
                        dto.setSubTotal(item.getSubTotal());
                        dto.setCreatedAt(item.getCreatedAt());
                        dto.setUpdatedAt(item.getUpdatedAt());
                        return dto;
                })
                .toList();

        invoiceResponse.setItems(itemResponse);
        return invoiceResponse;
    }

    private Authentication getAuth() {
        return  SecurityContextHolder.getContext().getAuthentication();
    }

    private boolean hasAnyRole(String... roles) {
        Authentication auth = getAuth();
        if (auth == null) {
            return false;
        }
        return auth.getAuthorities().stream()
                .anyMatch(a -> {
                    String authority = a.getAuthority();
                    for (String role : roles) {
                        if (("ROLE_" + role).equals(authority))
                            return true;
                    }
                    return false;
                });
    }

    private Employee getCurrentEmployee() {

        String username = getAuth().getName();

        return employeeRepo.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Employee profile not found"));
    }

    @Transactional
    public InvoiceResponse createInvoice(CreateInvoiceRequest request) {

        Invoice invoice = new Invoice();

        Employee employee = getCurrentEmployee();

        invoice.setEmployee(employee);

        if (request.getInvoiceType() == Invoice.InvoiceType.SALE) {

            if (request.getCustomerId() ==  null) {
                throw new InvoiceValidationException (" Customer Id is Required ");
            }
            if (request.getSupplierId() !=  null) {
                throw new InvoiceValidationException("Supplier must not be provided for SALE invoice");
            }

            Customer customer = customerRepo.findById(request.getCustomerId())
                    .orElseThrow(() -> new CustomerNotFoundException("Customer Not Found with Id :" + request.getCustomerId()));
            invoice.setCustomer(customer);
        }

        else if (request.getInvoiceType() == Invoice.InvoiceType.PURCHASE){

            if(request.getSupplierId() ==  null) {
                throw new  InvoiceValidationException (" Supplier Id is Required ");
            }
            if(request.getCustomerId() !=  null) {
                throw new InvoiceValidationException("Customer must not be provided for PURCHASE invoice");
            }

            Supplier supplier = supplierRepo.findById(request.getSupplierId())
                    .orElseThrow (()->new SupplierNotFoundException("Supplier Not Found with Id :"+ request.getSupplierId()));
            invoice.setSupplier(supplier);
        }else {
            throw new InvoiceValidationException("Invalid invoice type");
        }

        invoice.setInvoiceDate(request.getInvoiceDate());
        invoice.setDueDate(request.getDueDate());
        invoice.setInvoiceType(request.getInvoiceType());
        invoice.setPaymentStatus(Invoice.PaymentStatus.UNPAID);
        invoice.setInvoiceStatus(Invoice.InvoiceStatus.DRAFT);
        invoice.setPaidAmount(BigDecimal.ZERO);
        invoice.setNotes(request.getNotes());

        BigDecimal totalAmount = BigDecimal.ZERO;

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new InvoiceValidationException("Invoice must contain at least one item");
        }

        for (CreateInvoiceItemRequest itemRequest : request.getItems()){
            Product product = productRepo.findById(itemRequest.getProductId())
                    .orElseThrow (()-> new ProductNotFoundException("Product Not Found with id :" + itemRequest.getProductId()));

            Integer  quantity = itemRequest.getQuantity();

            if (quantity == null  ||  quantity <= 0 ){
                throw new QuantityMustBePositiveException("Quantity Must Be Positive");
            }

            if(itemRequest.getUnitPrice() == null || itemRequest.getUnitPrice().compareTo(BigDecimal.ZERO) < 0 ){
                throw new InvoiceValidationException("Unit price must be zero or positive");
            }

            BigDecimal discount = itemRequest.getDiscount() != null
                    ? itemRequest.getDiscount()
                    : BigDecimal.ZERO;

            if (discount.compareTo(BigDecimal.ZERO) < 0) {
                throw new InvoiceValidationException("Item discount cannot be negative");
            }

            BigDecimal subTotal =
                    itemRequest.getUnitPrice()
                            .multiply(BigDecimal.valueOf(quantity))
                            .subtract(discount);

            if (subTotal.compareTo(BigDecimal.ZERO) < 0){
                throw new InvoiceValidationException("Item discount cannot be greater than item total");
            }

            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setProduct(product);
            invoiceItem.setQuantity(quantity);
            invoiceItem.setUnitPrice(itemRequest.getUnitPrice());
            invoiceItem.setDiscount(discount);
            invoiceItem.setSubTotal(subTotal);

            invoice.addInvoiceItem(invoiceItem);

            totalAmount = totalAmount.add(subTotal);

        }

        BigDecimal invoiceDiscount  = request.getDiscountAmount() != null ?
                request.getDiscountAmount() : BigDecimal.ZERO;

        if (invoiceDiscount.compareTo(BigDecimal.ZERO) < 0){
            throw new InvoiceValidationException("Discount amount cannot be negative");
        }

        BigDecimal finalAmount = totalAmount.subtract(invoiceDiscount);

        if (finalAmount.compareTo(BigDecimal.ZERO) < 0){
            throw new InvoiceValidationException("Invoice discount cannot be greater than total amount");
        }

        invoice.setTotalAmount(totalAmount);
        invoice.setDiscountAmount(invoiceDiscount);
        invoice.setFinalAmount(finalAmount);
        invoice.setRemainingAmount(finalAmount);

        Invoice savedInvoice = invoiceRepo.save(invoice);
        savedInvoice.setInvoiceCode("INV-"+String.format("%03d", savedInvoice.getId()));
        return mapToInvoiceResponse(savedInvoice) ;
    }

    @Transactional
    public InvoiceResponse postInvoice (Integer id) {
        Invoice invoice = invoiceRepo.findById(id)
                .orElseThrow (()-> new InvoiceNotFoundException("Invoice Not Found with Id :" + id));

        if (invoice.getInvoiceStatus().equals(Invoice.InvoiceStatus.POSTED)){
            throw new  InvoiceAlreadyPostedException ("Invoice Already Posted");
        }
        if(invoice.getInvoiceStatus().equals(Invoice.InvoiceStatus.CANCELED)){
            throw new  CannotPostCanceledInvoiceException ("Canceled Invoice cannot be posted");
        }
        if(invoice.getInvoiceItems() == null || invoice.getInvoiceItems().isEmpty()){
            throw new InvoiceValidationException("Invoice must contain at least one item");
        }
            CreateInventoryTransactionRequest inventoryRequest = new CreateInventoryTransactionRequest();

            inventoryRequest.setEmployeeId(invoice.getEmployee().getId());
            inventoryRequest.setTransactionDate(LocalDate.now());

            if (invoice.getInvoiceType() == Invoice.InvoiceType.SALE){
                inventoryRequest.setTransactionType(InventoryTransaction.TransactionType.OUT);
            }else if (invoice.getInvoiceType() == Invoice.InvoiceType.PURCHASE){
                inventoryRequest.setTransactionType(InventoryTransaction.TransactionType.IN);
                inventoryRequest.setSupplierId(invoice.getSupplier().getId());
            }else {
                throw new InvalidInvoiceTypeException("Invalid invoice type");
            }

            List<CreateInventoryTransactionItemRequest> items = new ArrayList<>();

            for (InvoiceItem item : invoice.getInvoiceItems()) {

                CreateInventoryTransactionItemRequest  itemRequest = new CreateInventoryTransactionItemRequest();

                itemRequest.setProductId(item.getProduct().getId());
                itemRequest.setQuantity(item.getQuantity());
                itemRequest.setUnitCost(item.getUnitPrice());

                items.add(itemRequest);

            }

        inventoryRequest.setItems(items);
        inventoryRequest.setInvoiceId(invoice.getId());
        inventoryRequest.setNotes(invoice.getNotes());

        inventoryTransactionService.createInventoryTransaction(inventoryRequest);

        invoice.setInvoiceStatus(Invoice.InvoiceStatus.POSTED);
        Invoice savedInvoice = invoiceRepo.save(invoice);
        return  mapToInvoiceResponse(savedInvoice);
    }

    @Transactional
    public InvoiceResponse cancelPostedInvoice(Integer id) {
        Invoice invoice = invoiceRepo.findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice Not Found with Id :" + id));

        if (invoice.getInvoiceStatus() == Invoice.InvoiceStatus.CANCELED) {
            throw new InvoiceAlreadyCancelledException("Invoice Already Cancelled");
        }
        if (invoice.getInvoiceStatus() != Invoice.InvoiceStatus.POSTED) {
            throw new OnlyPostedInvoiceCanBeCanceledException("Only posted invoices can be canceled");
        }
        if (invoice.getPaidAmount().compareTo(BigDecimal.ZERO) > 0) {
            throw new InvoiceHasPaymentsException("Cannot cancel invoice with payments");
        }
        if (invoice.getInventoryTransaction() == null) {
            throw new InventoryTransactionNotFoundException("Invoice has no inventory transaction to reverse");
        }
        InventoryTransaction inventoryTransaction = invoice.getInventoryTransaction();

        inventoryTransactionService.reverseInventoryTransaction(inventoryTransaction.getId());

        invoice.setInvoiceStatus(Invoice.InvoiceStatus.CANCELED);
        Invoice savedInvoice = invoiceRepo.save(invoice);
        return mapToInvoiceResponse(savedInvoice);
    }

    public Page<InvoiceResponse> getAllInvoices(
             Invoice.InvoiceStatus status,
             Invoice.InvoiceType type,
             Integer customerId,
             Integer supplierId,
             Pageable pageable
    ){

        if ( Invoice.InvoiceType.SALE == type && supplierId != null ){
            throw new InvalidInvoiceTypeException ("Sale invoice cannot have supplier");
        }
        if (Invoice.InvoiceType.PURCHASE == type &&  customerId != null ){
            throw new InvalidInvoiceTypeException("Purchase invoice cannot have customer");
        }

        Specification<Invoice> spec = InvoiceSpecification.filter(status,type,customerId,supplierId);


            return  invoiceRepo.findAll(spec,pageable)
                .map(this::mapToInvoiceResponse);
    }


    public InvoiceResponse getInvoice(Integer id) {

        if(hasAnyRole("ADMIN","MANAGER")){
            Invoice invoice = invoiceRepo.findById(id)
                    .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found with id: " + id));

            return mapToInvoiceResponse(invoice);
        }
        Employee employee = getCurrentEmployee();

        Invoice invoice = invoiceRepo.findByIdAndEmployeeId(id,employee.getId())
                .orElseThrow(() -> new AccessDeniedException("You are not allowed to access this invoice"));

        return  mapToInvoiceResponse(invoice);
    }

    private void updateSupplierId(UpdateInvoiceRequest request, Invoice invoice) {

        if (request.getSupplierId() == null) {
            return;
        }
        if (invoice.getInvoiceType() != Invoice.InvoiceType.PURCHASE) {
            throw new InvalidInvoiceTypeException("SALE invoice cannot have supplier");
        }
        Supplier supplier = supplierRepo.findById(request.getSupplierId())
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found with id: " + request.getSupplierId()));
        invoice.setSupplier(supplier);
    }


    private void updateCustomerId(UpdateInvoiceRequest request, Invoice invoice) {

        if (request.getCustomerId() == null) {
            return;
        }

        if (invoice.getInvoiceType() != Invoice.InvoiceType.SALE) {
            throw new InvalidInvoiceTypeException("PURCHASE invoice cannot have customer");
        }

        Customer customer = customerRepo.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + request.getCustomerId()));
        invoice.setCustomer(customer);
    }

    private void updateBasicInvoiceFields(UpdateInvoiceRequest request, Invoice invoice) {

        if(request.getInvoiceDate() != null) {
            invoice.setInvoiceDate(request.getInvoiceDate());
        }

        if(request.getDueDate() != null) {
            invoice.setDueDate(request.getDueDate());
        }
        if(request.getDiscountAmount() != null) {
            if(request.getDiscountAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new InvoiceValidationException("Discount amount cannot be negative");
            }
            invoice.setDiscountAmount(request.getDiscountAmount());
        }
        if(request.getPaidAmount() != null) {
            if(request.getPaidAmount().compareTo(BigDecimal.ZERO) < 0) {
                throw new InvoiceValidationException("Paid amount cannot be negative");
            }
        }
        if(request.getPaidAmount() != null){
            invoice.setPaidAmount(request.getPaidAmount());
        }
        if(request.getPaymentStatus() != null) {
            invoice.setPaymentStatus(request.getPaymentStatus());
        }
        if(request.getNotes() != null){
            invoice.setNotes(request.getNotes());
        }
    }

    private void updateInvoiceItem(UpdateInvoiceRequest request, Invoice invoice) {
        if(request.getItems() == null || request.getItems().isEmpty()){
            return;
        }
        for (UpdateInvoiceItemRequest requestItem : request.getItems()) {

            if(requestItem.getId() == null){
                throw new InvoiceItemIdRequiredException ("Invoice item id required");
            }
            InvoiceItem invoiceItem = invoiceItemRepo.findById(requestItem.getId())
                    .orElseThrow (()-> new InvoiceItemNotFoundException("Invoice item not found with id: " + requestItem.getId()) );

            if(!invoiceItem.getInvoice().getId().equals(invoice.getId())) {
                throw new InvoiceItemDoesNotBelongToInvoiceException("Invoice item does not belong to this invoice");
            }

            if (requestItem.getProductId() != null) {
                Product product = productRepo.findById(requestItem.getProductId())
                        .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + requestItem.getProductId()));
                invoiceItem.setProduct(product);
            }
            if (requestItem.getQuantity() != null) {
                if(requestItem.getQuantity() <= 0 ){
                    throw new InvoiceValidationException("Quantity must be positive");
                }
                invoiceItem.setQuantity(requestItem.getQuantity());
            }
            if(requestItem.getUnitPrice() != null) {
                if(requestItem.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {
                    throw new InvoiceValidationException("Unit price cannot be negative");
                }
                invoiceItem.setUnitPrice(requestItem.getUnitPrice());
            }
            if(requestItem.getDiscount() != null) {
                if(requestItem.getDiscount().compareTo(BigDecimal.ZERO) < 0) {
                    throw new InvoiceValidationException("Discount cannot be negative");
                }
                invoiceItem.setDiscount(requestItem.getDiscount());
            }
            recalculateInvoiceItemsSubTotal(invoiceItem);
        }
    }

    private void recalculateInvoiceItemsSubTotal(InvoiceItem invoiceItem) {
        BigDecimal subTotal = invoiceItem.getUnitPrice()
                .multiply(BigDecimal.valueOf(invoiceItem.getQuantity()))
                .subtract(invoiceItem.getDiscount());

        if (subTotal.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvoiceValidationException("Item discount cannot be greater than item total");
        }
        invoiceItem.setSubTotal(subTotal);
    }

    private void recalculateInvoiceTotal(Invoice invoice) {

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal discount = invoice.getDiscountAmount();


        for (InvoiceItem invoiceItem : invoice.getInvoiceItems()) {
            total = total.add(invoiceItem.getSubTotal());
        }

        BigDecimal finalAmount = invoice.getFinalAmount();
        finalAmount = total.subtract(discount);

        if (finalAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvoiceValidationException("Invoice discount cannot be greater than total");
        }
        BigDecimal paidAmount = invoice.getPaidAmount();

        BigDecimal remainingAmount  = BigDecimal.ZERO;
        remainingAmount = finalAmount.subtract(paidAmount);

        invoice.setTotalAmount(total);
        invoice.setFinalAmount(finalAmount);
        invoice.setRemainingAmount(remainingAmount);
    }

    private Invoice getInvoiceWithAccess(Integer invoiceId) {

        if (hasAnyRole("ADMIN", "MANAGER")) {
            return invoiceRepo.findById(invoiceId)
                    .orElseThrow(() -> new InvoiceNotFoundException(
                            "Invoice not found with id: " + invoiceId
                    ));
        }

        Employee employee = getCurrentEmployee();

        return invoiceRepo.findByIdAndEmployeeId(invoiceId, employee.getId())
                .orElseThrow(() -> new AccessDeniedException(
                        "You are not allowed to access this invoice"
                ));
    }

    @Transactional
    public InvoiceResponse updateDraftInvoice( Integer id ,UpdateInvoiceRequest request) {

        Invoice invoice =   getInvoiceWithAccess(id);

        if (invoice.getInvoiceStatus() != Invoice.InvoiceStatus.DRAFT) {
            throw new OnlyDraftInvoiceCanBeUpdatedException("Only Draft Invoice can be updated");
        }

        updateSupplierId(request,invoice);
        updateCustomerId(request,invoice);
        updateBasicInvoiceFields(request,invoice);
        updateInvoiceItem(request,invoice);
        recalculateInvoiceTotal(invoice);

        Invoice savedInvoice = invoiceRepo.save(invoice);

        return mapToInvoiceResponse(savedInvoice);

    }

    @Transactional
    public void deleteDraftInvoice(Integer id) {

        Invoice invoice = invoiceRepo.findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found with id: " + id));

        if(invoice.getInvoiceStatus() != Invoice.InvoiceStatus.DRAFT){
            throw new OnlyDraftInvoiceCanBeDeleted ("Only Draft Invoice can be deleted");
        }

        invoiceRepo.delete(invoice);
    }

    @Transactional
    public InvoiceResponse deleteDraftItem (Integer invoiceId ,Integer invoiceItemId) {

        Invoice invoice = getInvoiceWithAccess(invoiceId) ;

        if (invoice.getInvoiceStatus() != Invoice.InvoiceStatus.DRAFT) {
            throw new OnlyDraftInvoiceCanBeUpdatedException("Only Draft Invoice can be updated");
        }
        InvoiceItem invoiceItem = invoiceItemRepo.findById(invoiceItemId)
                .orElseThrow(() -> new InvoiceItemNotFoundException("Invoice item not found with id: " + invoiceItemId));

        if (!invoiceItem.getInvoice().getId().equals(invoice.getId())) {
            throw new InvoiceItemDoesNotBelongToInvoiceException(
                    "Invoice item does not belong to this invoice"
            );
        }

        if (invoice.getInvoiceItems().size() <= 1) {
            throw new InvoiceMustContainAtLeastOneItemException(
                    "Invoice must contain at least one item"
            );
        }
        invoice.getInvoiceItems().remove(invoiceItem);
        invoiceItemRepo.delete(invoiceItem);

        recalculateInvoiceTotal(invoice);
        Invoice savedInvoice = invoiceRepo.save(invoice);
        return mapToInvoiceResponse(savedInvoice);

    }
}
