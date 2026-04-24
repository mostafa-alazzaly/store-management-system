package com.store.management.system.store_management.dto;

import com.store.management.system.store_management.entity.Employee;
import com.store.management.system.store_management.validation.NotBlankIfPresent;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateEmployeeRequest {

    @NotBlankIfPresent(message="First name can't be empty")
    private String firstName;
    @NotBlankIfPresent(message="Last name can't be empty")
    private String lastName;
    private String address;
    private String zipCode;
    @NotBlankIfPresent(message="Job title can't be empty")
    private String jobTitle;
    private Employee.Status status;
    @Positive(message="Salary must be positive")
    private BigDecimal salary;
    @PastOrPresent(message="Hire date can't be in the future ")
    private LocalDate hireDate;
    private Integer userId;
    public UpdateEmployeeRequest() {}

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

