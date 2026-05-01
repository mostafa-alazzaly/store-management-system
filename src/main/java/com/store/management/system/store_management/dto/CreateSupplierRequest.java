package com.store.management.system.store_management.dto;
import com.store.management.system.store_management.entity.Supplier;
import com.store.management.system.store_management.validation.NotNullString;
import jakarta.validation.constraints.NotBlank;

public class CreateSupplierRequest {

    @NotNullString(message="Company name can't be 'null'")
    @NotBlank(message="Company name is required")
    private String companyName;

    @NotNullString(message="Company phone can't be 'null'")
    @NotBlank(message="Company phone is required")
    private String companyPhone;

    @NotNullString(message="Contact name can't be 'null'")
    @NotBlank(message="Contact person name is required")
    private String contactPersonName;

    private String contactPersonPhone;

    @NotNullString(message="Email can't be 'null'")
    @NotBlank(message="Email is required")
    private String email;

    @NotNullString(message="Address can't be 'null'")
    @NotBlank(message="Address is required")
    private String address;

    @NotNullString(message="City can't be 'null'")
    @NotBlank(message="City is required")
    private String city;

    @NotNullString(message="Country can't be 'null'")
    @NotBlank(message="Country is required")
    private String country;

    private String zipCode;
    private String website;
    private String taxNumber;
    private String notes;
    private Supplier.PaymentTerms paymentTerms;
    private Supplier.Status status;

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public String getEmail() {
        return email;
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

    public String getWebsite() {
        return website;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public String getNotes() {
        return notes;
    }

    public Supplier.PaymentTerms getPaymentTerms() {
        return paymentTerms;
    }

    public Supplier.Status getStatus() {
        return status;
    }
}
