package com.store.management.system.store_management.dto.customer.request;

import com.store.management.system.store_management.entity.Customer;
import com.store.management.system.store_management.validation.NotBlankIfPresent;

public class UpdateCustomerRequest {

    @NotBlankIfPresent(message="First name can't be empty")
    private  String firstName;
    @NotBlankIfPresent(message="Last name can't be empty")
    private String lastName;
    @NotBlankIfPresent(message="Address can't be empty")
    private String address;
    @NotBlankIfPresent(message="City can't be empty")
    private String city;
    @NotBlankIfPresent(message="Country can't be empty")
    private String country;
    private String zipCode;
    @NotBlankIfPresent(message="Shop name can't be empty")
    private String shopName;
    private Customer.Status status ;
    private String notes;
    private Integer userId;
    public UpdateCustomerRequest() {}

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

    public String getNotes() {
        return notes;
    }

    public Integer getUserId() {
        return userId;
    }

    public Customer.Status getStatus() {
        return status;
    }
}
