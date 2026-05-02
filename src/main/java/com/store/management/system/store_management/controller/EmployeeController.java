package com.store.management.system.store_management.controller;


import com.store.management.system.store_management.dto.employee.request.CreateEmployeeRequest;
import com.store.management.system.store_management.dto.employee.response.EmployeeResponse;
import com.store.management.system.store_management.dto.employee.request.UpdateEmployeeRequest;
import com.store.management.system.store_management.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {


    private final EmployeeService employeeService;
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeResponse> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Integer id){
        return employeeService.getEmployeeById(id) ;
    }

    @PostMapping
    public EmployeeResponse createEmployee (@Valid @RequestBody CreateEmployeeRequest request){
       return  employeeService.createEmployee(request);
    }

    @PatchMapping("/{id}")
    public EmployeeResponse updateEmployee (@PathVariable Integer id, @Valid @RequestBody UpdateEmployeeRequest request){
        return employeeService.updateEmployee(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee (@PathVariable Integer id){
         employeeService.deleteEmployee(id);
         return "Employee deleted successfully with id : " + id;
    }



}
