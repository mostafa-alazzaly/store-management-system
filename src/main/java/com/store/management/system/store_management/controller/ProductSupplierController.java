package com.store.management.system.store_management.controller;

import com.store.management.system.store_management.dto.productSupplier.request.CreateProductSupplierRequest;
import com.store.management.system.store_management.dto.productSupplier.response.ProductSupplierResponse;
import com.store.management.system.store_management.dto.productSupplier.request.UpdateProductSupplierRequest;
import com.store.management.system.store_management.service.ProductSupplierService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product-suppliers")
public class ProductSupplierController {

    private final ProductSupplierService productSupplierService;
    public ProductSupplierController(ProductSupplierService productSupplierService) {
        this.productSupplierService = productSupplierService;
    }
    @GetMapping("/all")
    public Page<ProductSupplierResponse> getAllProductSuppliers(Pageable pageable){
        return productSupplierService.findAllProductSuppliers(pageable);
    }

    @GetMapping
    public Page<ProductSupplierResponse> findAllActiveProductSuppliers(Pageable pageable){
        return productSupplierService.findAllActiveProductSuppliers(pageable);
    }

    @GetMapping("/{id}")
    public ProductSupplierResponse getProductSupplierById(@PathVariable Integer id){
        return productSupplierService.findProductSupplierById(id);
    }

    @PostMapping
    public ProductSupplierResponse createProductSupplier (@Valid @RequestBody CreateProductSupplierRequest request){
        return productSupplierService.createProductSupplier(request);
    }

    @PatchMapping("/{id}")
    public ProductSupplierResponse updateProductSupplier(@PathVariable Integer id, @Valid @RequestBody UpdateProductSupplierRequest request){
        return productSupplierService.updateProductSupplier(id, request);
    }

    @PatchMapping("/{id}/deactivate")
    public String deactivateProductSupplier(@PathVariable Integer id){
        productSupplierService.deactivateProductSupplier(id);
        return "Product Supplier deactivated successfully with id: " + id;
    }
    @PatchMapping("/{id}/activate")
    public String activateProductSupplier(@PathVariable Integer id){
        productSupplierService.activateProductSupplier(id);
        return "Product Supplier activated successfully with id: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteProductSupplierById(@PathVariable Integer id){
        productSupplierService.deleteProductSupplier(id);
        return "Product Supplier deleted successfully :" + id;
    }
}
