package com.store.management.system.store_management.dto.employee.response;

import com.store.management.system.store_management.entity.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeResponse {

    private int id ;
    private String firstName;
    private String lastName;
    private String address;
    private String zipCode;
    private String jobTitle;
    private Employee.Status status;
    private BigDecimal salary;
    private LocalDate hireDate;
    
    public EmployeeResponse () {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Employee.Status getStatus() {
        return status;
    }

    public void setStatus(Employee.Status status) {
        this.status = status;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
}
