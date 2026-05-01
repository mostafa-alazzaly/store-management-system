package com.store.management.system.store_management.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="supplier_code")
    private String supplierCode;

    @Column(name="company_name",nullable= false)
    private String companyName;

    @Column(name="company_phone",nullable= false)
    private String companyPhone;

    @Column(name="contact_person_name",nullable= false)
    private String contactPersonName;

    @Column(name="contact_person_phone")
    private String contactPersonPhone;

    @Column(name="email",nullable= false)
    private String email;

    @Column(name="address",nullable= false)
    private String address;

    @Column(name="city",nullable= false)
    private String city;

    @Column(name="country",nullable= false)
    private String country;

    @Column(name="zip_code")
    private String zipCode;

    @Column(name="website")
    private String website;

    @Column(name="tax_number")
    private String taxNumber;

    @Column(name="notes")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name="payment_terms",nullable= false)
    private PaymentTerms paymentTerms;

    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable= false)
    private Status status;

    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy="supplier")
    private List<ProductSupplier> productSuppliers;

    public enum PaymentTerms{
        CASH,
        NET_15,
        NET_30,
        PARTIAL,
    }
    public enum Status{
        ACTIVE,
        INACTIVE,
        BLOCKED,
    }
    public void add(ProductSupplier productSupplier){
        if (productSuppliers == null){
             productSuppliers = new ArrayList<>();
        }
        productSuppliers.add(productSupplier);
        productSupplier.setSupplier(this);
    }

    public Supplier(){}

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

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

    public PaymentTerms getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(PaymentTerms paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ProductSupplier> getProductSuppliers() {
        return productSuppliers;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setProductSuppliers(List<ProductSupplier> productSuppliers) {
        this.productSuppliers = productSuppliers;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierCode='" + supplierCode + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyPhone='" + companyPhone + '\'' +
                ", contactPersonName='" + contactPersonName + '\'' +
                ", contactPersonPhone='" + contactPersonPhone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", website='" + website + '\'' +
                ", taxNumber='" + taxNumber + '\'' +
                ", notes='" + notes + '\'' +
                ", paymentTerms=" + paymentTerms +
                ", status=" + status +
                '}';
    }
}
