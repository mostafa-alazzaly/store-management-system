package com.store.management.system.store_management.controller;

import com.store.management.system.store_management.dto.CreateSupplierRequest;
import com.store.management.system.store_management.dto.SupplierResponse;
import com.store.management.system.store_management.dto.UpdateSupplierRequest;
import com.store.management.system.store_management.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;
    @Autowired
    public SupplierController(SupplierService supplierService){
        this.supplierService = supplierService;
    }
    @GetMapping("/all")
    public List<SupplierResponse> getSuppliers(){
        return supplierService.getAllSuppliers() ;
    }

    @GetMapping
    public List<SupplierResponse> getActiveSuppliers(){
        return supplierService.getActiveSuppliers();
    }

    @GetMapping("/{id}")
    public SupplierResponse getSupplierById(@PathVariable Integer id){
        return supplierService.getSupplierById(id);
    }

    @PostMapping
    public SupplierResponse createSupplier(@Valid @RequestBody CreateSupplierRequest request){
        return  supplierService.createSupplier(request);
    }

    @PatchMapping("/{id}")
    public SupplierResponse updateSupplier(@PathVariable Integer id, @Valid @RequestBody UpdateSupplierRequest request){
        return supplierService.updateSupplier(id, request);
    }

    @PatchMapping("/{id}/deactivate")
    public String deactivateSupplier(@PathVariable Integer id){
        supplierService.deactivateSupplier(id);
        return "Supplier deactivated successfully with id : " + id ;
    }

    @PatchMapping("/{id}/activate")
    public String activateSupplier(@PathVariable Integer id){
        supplierService.activateSupplier(id);
        return "Supplier activated successfully with id : " + id ;
    }

    @DeleteMapping("/{id}")
    public String deleteSupplier(@PathVariable Integer id){
        supplierService.deleteSupplierById(id);
        return "Supplier deleted successfully with id : " + id;
    }
}
