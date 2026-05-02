package com.store.management.system.store_management.dto.supplier.request;

import com.store.management.system.store_management.entity.Supplier;
import com.store.management.system.store_management.validation.NotBlankIfPresent;

public class UpdateSupplierRequest {


    @NotBlankIfPresent(message="Company name can't be empty")
    private String companyName;

    @NotBlankIfPresent(message="Company Phone can't be empty")
    private String companyPhone;

    @NotBlankIfPresent(message="Contact person name can't be empty")
    private String contactPersonName;

    private String contactPersonPhone;

    @NotBlankIfPresent(message="Email can't be empty")
    private String email;

    @NotBlankIfPresent(message="Address can't be empty")
    private String address;

    @NotBlankIfPresent(message="City can't be empty")
    private String city;

    @NotBlankIfPresent(message="Country can't be empty")
    private String country;

    private String zipCode;
    private String website;
    private String taxNumber;
    private String notes;
    private Supplier.PaymentTerms paymentTerms;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonPhone() {
        return contactPersonPhone;
    }

    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Supplier.PaymentTerms getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(Supplier.PaymentTerms paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

}
