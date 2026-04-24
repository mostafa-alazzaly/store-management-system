package com.store.management.system.store_management.controller;

import com.store.management.system.store_management.dto.CreateCustomerRequest;
import com.store.management.system.store_management.dto.CustomerResponse;
import com.store.management.system.store_management.dto.UpdateCustomerRequest;
import com.store.management.system.store_management.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    @Autowired
    public  CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }
    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Integer id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public CustomerResponse createCustomer(@Valid @RequestBody CreateCustomerRequest request){
        return customerService.createCustomer(request);
    }

    @PatchMapping("/{id}")
    public CustomerResponse updateCustomer(@PathVariable Integer id, @Valid @RequestBody UpdateCustomerRequest request){
        return customerService.updateCustomer(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Integer id){
        customerService.deleteCustomerById(id);
        return "Customer Deleted Successfully with id : " + id;
    }
}
