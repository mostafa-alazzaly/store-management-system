package com.store.management.system.store_management.dto.customer.request;

import com.store.management.system.store_management.entity.Customer;
import com.store.management.system.store_management.validation.NotNullString;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateCustomerRequest {

    @NotNullString(message= "First name can't be 'null'")
    @NotBlank(message= "first name is required")
    private  String firstName;

    @NotNullString(message="Last name can't be 'null'")
    @NotBlank(message="last name is required")
    private String lastName;

    @NotNullString(message="Address can't be 'null'")
    @NotBlank(message="address is required")
    private String address;

    @NotNullString(message="City can't be 'null'")
    @NotBlank(message="city is required")
    private String city;

    @NotNullString(message="Country can't be 'null'")
    @NotBlank(message="country is required")
    private String country;

    private String zipCode;

    @NotNullString(message="Shop name can't be 'null'")
    @NotBlank(message="Shop name is required")
    private String shopName;

    private Customer.Status  status;
    private String notes;

    @NotNull(message="User id is required")
    private Integer userId;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getShopName() {
        return shopName;
    }

    public Customer.Status getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public Integer getUserId() {
        return userId;
    }
}
