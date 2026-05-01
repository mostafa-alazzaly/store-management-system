package com.store.management.system.store_management.controller;

import com.store.management.system.store_management.dto.CreateProductSupplierRequest;
import com.store.management.system.store_management.dto.ProductSupplierResponse;
import com.store.management.system.store_management.dto.UpdateProductSupplierRequest;
import com.store.management.system.store_management.service.ProductSupplierService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-suppliers")
public class ProductSupplierController {

    private final ProductSupplierService productSupplierService;
    public ProductSupplierController(ProductSupplierService productSupplierService) {
        this.productSupplierService = productSupplierService;
    }
    @GetMapping("/all")
    public List<ProductSupplierResponse> getAllProductSuppliers(){
        return productSupplierService.findAllProductSuppliers();
    }

    @GetMapping
    public List<ProductSupplierResponse> findAllActiveProductSuppliers(){
        return productSupplierService.findAllActiveProductSuppliers();
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
