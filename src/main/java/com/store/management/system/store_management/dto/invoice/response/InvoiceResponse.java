package com.store.management.system.store_management.dto.invoice.response;

import com.store.management.system.store_management.dto.invoiceItem.response.InvoiceItemResponse;
import com.store.management.system.store_management.entity.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class InvoiceResponse {
    private Integer id ;
    private Integer employeeId;
    private String invoiceCode;
    private Integer supplierId;
    private Integer customerId;
    private List<InvoiceItemResponse> items;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private BigDecimal paidAmount;
    private BigDecimal remainingAmount;
    private Invoice.InvoiceType invoiceType;
    private Invoice.PaymentStatus paymentStatus;
    private Invoice.InvoiceStatus invoiceStatus;
    private String notes ;

    public InvoiceResponse() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<InvoiceItemResponse> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItemResponse> items) {
        this.items = items;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public Invoice.InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Invoice.InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Invoice.PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Invoice.PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Invoice.InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Invoice.InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
