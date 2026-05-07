package com.store.management.system.store_management.dto.invoice.request;

import com.store.management.system.store_management.dto.invoiceItem.request.UpdateInvoiceItemRequest;
import com.store.management.system.store_management.entity.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class UpdateInvoiceRequest {
    private Integer supplierId;
    private Integer customerId;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private BigDecimal discountAmount;
    private BigDecimal paidAmount;
    private Invoice.PaymentStatus paymentStatus;
    private Invoice.InvoiceStatus invoiceStatus;
    private String notes ;
    private List<UpdateInvoiceItemRequest> items;


    public UpdateInvoiceRequest(){}

    public Integer getSupplierId() {
        return supplierId;
    }

    public List<UpdateInvoiceItemRequest> getItems() {
        return items;
    }

    public void setItems(List<UpdateInvoiceItemRequest> items) {
        this.items = items;
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

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
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
