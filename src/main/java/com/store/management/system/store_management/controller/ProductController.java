package com.store.management.system.store_management.controller;

import com.store.management.system.store_management.dto.CreateProductRequest;
import com.store.management.system.store_management.dto.ProductResponse;
import com.store.management.system.store_management.dto.UpdateProductBarcodeRequest;
import com.store.management.system.store_management.dto.UpdateProductRequest;
import com.store.management.system.store_management.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping
    public List<ProductResponse> getAllActiveProducts(){
        return productService.getAllActiveProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Integer id){
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductResponse createProduct (@Valid @RequestBody CreateProductRequest request){
        return productService.createProduct(request);
    }

    @PatchMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Integer id, @Valid @RequestBody UpdateProductRequest request){
        return productService.updateProduct(id, request);
    }

    @PatchMapping("/{id}/deactivate")
    public String deactivateProduct(@PathVariable Integer id){
        productService.deactivateProduct(id);
        return "Product deactivated successfully with id : " + id;
    }
    @PatchMapping("/{id}/activate")
    public String activateProduct(@PathVariable Integer id){
        productService.activateProduct(id);
        return "Product activated successfully with id : " + id;
    }

    @PatchMapping("/{id}/barcode")
    public String barcodeProduct(@PathVariable Integer id, @RequestBody UpdateProductBarcodeRequest request){
        productService.updateBarcode(id, request);
        return "Product barcode updated successfully with id : " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
        return "Product deleted successfully : " + id;
    }

}
