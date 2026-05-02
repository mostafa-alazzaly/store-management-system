package com.store.management.system.store_management.dto.employee.request;


import com.store.management.system.store_management.entity.Employee;
import com.store.management.system.store_management.validation.NotNullString;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateEmployeeRequest {

    @NotNullString(message = "First name can't be 'null'")
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotNullString(message = "Last name can't be 'null'")
    @NotBlank(message = "Last name is required")
    private String lastName;

    private String address;

    @Size(max = 20)
    private String zipCode;

    @NotNullString(message = "Job Title can't be 'null'")
    @NotBlank(message = "Job Title is required")
    @Size(max = 150)
    private String jobTitle;

    private Employee.Status status;

    @NotNull(message = "Salary is required ")
    @DecimalMin(value = "0.0", inclusive = false, message = "salary must be positive ")
    private BigDecimal salary;

    @NotNull(message = "Hire date is required ")
    @PastOrPresent(message = "Hire date must be in the past or present ")
    private LocalDate hireDate;

    @NotNull(message = "User id is required ")
    private int userId;

    public CreateEmployeeRequest() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public int getUserId() {
        return userId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Employee.Status getStatus() {
        return status;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }
}
