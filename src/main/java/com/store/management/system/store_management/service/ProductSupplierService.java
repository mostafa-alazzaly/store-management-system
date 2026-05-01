package com.store.management.system.store_management.service;

import com.store.management.system.store_management.dto.CreateProductSupplierRequest;
import com.store.management.system.store_management.dto.ProductSupplierResponse;
import com.store.management.system.store_management.dto.UpdateProductSupplierRequest;
import com.store.management.system.store_management.entity.Product;
import com.store.management.system.store_management.entity.ProductSupplier;
import com.store.management.system.store_management.entity.Supplier;
import com.store.management.system.store_management.exception.*;
import com.store.management.system.store_management.repo.ProductRepo;
import com.store.management.system.store_management.repo.ProductSupplierRepo;
import com.store.management.system.store_management.repo.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductSupplierService {
    private final ProductSupplierRepo productSupplierRepo ;
    private final ProductRepo productRepo;
    private final SupplierRepo supplierRepo;
    @Autowired
    public ProductSupplierService(ProductRepo productRepo, SupplierRepo supplierRepo, ProductSupplierRepo productSupplierRepo) {
        this.productRepo = productRepo;
        this.supplierRepo = supplierRepo;
        this.productSupplierRepo = productSupplierRepo;
    }

    private ProductSupplierResponse mapToProductSupplierResponse(ProductSupplier productSupplier) {

        ProductSupplierResponse productSupplierResponse = new ProductSupplierResponse();

        productSupplierResponse.setId(productSupplier.getId());
        productSupplierResponse.setSupplierId(productSupplier.getSupplier().getId());
        productSupplierResponse.setProductId(productSupplier.getProduct().getId());
        productSupplierResponse.setSupplierProductCode(productSupplier.getProductSupplierCode());
        productSupplierResponse.setPurchasePrice(productSupplier.getPurchasePrice());
        productSupplierResponse.setMinOrderQuantity(productSupplier.getMinOrderQuantity());
        productSupplierResponse.setLeadTimeDays(productSupplier.getLeadTimeDays());
        productSupplierResponse.setPrimary(productSupplier.getPrimary());
        productSupplierResponse.setCreatedAt(productSupplier.getCreatedAt());
        productSupplierResponse.setUpdatedAt(productSupplier.getUpdatedAt());
        productSupplierResponse.setStatus(productSupplier.getStatus());
        return productSupplierResponse;
    }
    public List<ProductSupplierResponse> findAllProductSuppliers() {
        return productSupplierRepo.findAll()
                .stream()
                .map(this::mapToProductSupplierResponse)
                .toList();
    }

    public List<ProductSupplierResponse> findAllActiveProductSuppliers() {
        return productSupplierRepo.findByStatus(ProductSupplier.Status.ACTIVE)
                .stream()
                .map(this::mapToProductSupplierResponse)
                .toList();
    }

    public ProductSupplierResponse findProductSupplierById(Integer id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = auth != null && auth.getAuthorities()
                    .stream()
                    .anyMatch(a->"ROLE_ADMIN".equals(a.getAuthority()));

        ProductSupplier productSupplier;
        if (isAdmin) {
             productSupplier = productSupplierRepo.findById(id)
                    .orElseThrow (()-> new ProductSupplierNotFountException(" Product supplier with id : " + id + " not found"));
        }else {
            productSupplier = productSupplierRepo.findByIdAndStatus( id,ProductSupplier.Status.ACTIVE)
                    .orElseThrow  (()-> new ProductSupplierNotFountException(" Product supplier with id : " + id + " not found"));
        }

        return mapToProductSupplierResponse(productSupplier);
    }

    @Transactional
    public ProductSupplierResponse createProductSupplier (CreateProductSupplierRequest request){
        ProductSupplier productSupplier = new ProductSupplier();

        productSupplier.setPrimary(
                request.getPrimary() != null ?  request.getPrimary() : false);
        productSupplier.setStatus(request.getStatus() != null ? request.getStatus() : ProductSupplier.Status.ACTIVE);
        productSupplier.setLeadTimeDays(request.getLeadTimeDays());
        productSupplier.setPurchasePrice(request.getPurchasePrice());
        productSupplier.setMinOrderQuantity(request.getMinOrderQuantity());

        Supplier supplier = supplierRepo.findById(request.getSupplierId())
                .orElseThrow (()-> new SupplierNotFoundException ("Supplier not found : " + request.getSupplierId()));
        productSupplier.setSupplier(supplier);

        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(()-> new ProductNotFoundException("Product not found : " + request.getProductId()));
        productSupplier.setProduct(product);

        if (productSupplierRepo.existsBySupplierIdAndProductId(request.getSupplierId(), request.getProductId())) {
            throw new ProductSupplierAlreadyExistsException ( "Supplier with id " + request.getSupplierId() +
                    " is already assigned to product with id " + request.getProductId());
        }

        ProductSupplier savedProductSupplier = productSupplierRepo.save(productSupplier);

        productSupplier.setProductSupplierCode(
                supplier.getSupplierCode() + "-" + product.getProductCode());

        return  mapToProductSupplierResponse(savedProductSupplier);

    }

    private Authentication getAuth(){
        return  SecurityContextHolder.getContext().getAuthentication();
    }
    private boolean hasAnyRole(String... roles){
        Authentication  auth = getAuth();
        if (auth == null) {
            return false;
        }return auth.getAuthorities()
                .stream()
                .anyMatch(a->{
                    String authority = a.getAuthority();
                    for (String role : roles) {
                        if (("ROLE_" + role).equals(authority))
                             return true;
                    }
                    return false;
                });
    }
    private void requireAdminOrManager() {
        if(!hasAnyRole("ADMIN","MANAGER")) {
            throw new  OperationNotAllowedException ("Only admin or manager can perform this action");
        }
    }
    private void updatePurchasePrice (ProductSupplier productSupplier, UpdateProductSupplierRequest request) {
        if(request.getPurchasePrice() != null){
            productSupplier.setPurchasePrice(request.getPurchasePrice());
        }
    }
    private void updateMinOrderQuantity (ProductSupplier productSupplier, UpdateProductSupplierRequest request) {
        if(request.getMinOrderQuantity() != null){
            productSupplier.setMinOrderQuantity(request.getMinOrderQuantity());
        }
    }
    private void updateLeadTimeDays (ProductSupplier productSupplier, UpdateProductSupplierRequest request) {
        if(request.getLeadTimeDays() != null){
            productSupplier.setLeadTimeDays(request.getLeadTimeDays());
        }
    }
    private void updateIsPrimary (ProductSupplier productSupplier, UpdateProductSupplierRequest request) {
        if(request.getPrimary() != null){
            requireAdminOrManager();
            productSupplier.setPrimary(request.getPrimary());
        }
    }
    @Transactional
    public ProductSupplierResponse updateProductSupplier (Integer id, UpdateProductSupplierRequest request){
        ProductSupplier productSupplier = productSupplierRepo.findById(id)
                        .orElseThrow (()->new ProductSupplierNotFountException ("Product supplier with id : " + id + " not found") );

        updatePurchasePrice(productSupplier, request);
        updateMinOrderQuantity(productSupplier, request);
        updateLeadTimeDays(productSupplier, request);
        updateIsPrimary(productSupplier, request);

        return mapToProductSupplierResponse(productSupplier);
    }

    @Transactional
    public void deactivateProductSupplier(Integer id){
        ProductSupplier productSupplier = productSupplierRepo.findById(id)
                .orElseThrow (()->new ProductSupplierNotFountException("Product supplier with id : " + id + " not found") );

        if (productSupplier.getStatus() == ProductSupplier.Status.INACTIVE){
            throw new ProductSupplierAlreadyDeactivatedException ("Product supplier with id : " + id + " is already deactivated");
        }
        productSupplier.setStatus(ProductSupplier.Status.INACTIVE);
        productSupplier.setPrimary(false);
    }

    @Transactional
    public void activateProductSupplier(Integer id){
        ProductSupplier productSupplier = productSupplierRepo.findById(id)
                .orElseThrow (()->new ProductSupplierNotFountException("Product supplier with id : " + id + " not found") );

        if (productSupplier.getStatus() == ProductSupplier.Status.ACTIVE){
            throw new ProductSupplierAlreadyActivatedException("Product supplier with id : " + id + " is already activated");
        }

        productSupplier.setStatus(ProductSupplier.Status.ACTIVE);
    }

    @Transactional
    public void deleteProductSupplier (Integer id){
        ProductSupplier productSupplier = productSupplierRepo.findById(id)
                        .orElseThrow (()->new ProductSupplierNotFountException("Product supplier with id : " + id + " not found") );

        if (productSupplier.getStatus() == ProductSupplier.Status.ACTIVE){
            throw new CannotDeleteActiveProductSupplierException ("Product supplier with id : " + id + " is already active");
        }
        productSupplierRepo.delete(productSupplier);
    }
}
