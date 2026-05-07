package com.store.management.system.store_management.dto.invoice.request;

import com.store.management.system.store_management.dto.invoiceItem.request.CreateInvoiceItemRequest;
import com.store.management.system.store_management.entity.Invoice;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CreateInvoiceRequest {

    private Integer supplierId;
    private Integer customerId;

    @Valid
    @NotNull(message="Invoice Items are required")
    private List<CreateInvoiceItemRequest> items;

    @PastOrPresent(message = "Invoice date cannot be in the future")
    @NotNull(message="Invoice Date is required ")
    private LocalDate invoiceDate;

    @FutureOrPresent (message="Due date must be today or in the future")
    @NotNull(message="Due Date is required ")
    private LocalDate dueDate;

    @PositiveOrZero(message="Discount Amount can't be negative")
    @NotNull(message="Discount Amount is required")
    private BigDecimal discountAmount;

    @NotNull(message="Invoice Type is required")
    private Invoice.InvoiceType invoiceType;

    private String notes ;

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

    public List<CreateInvoiceItemRequest> getItems() {
        return items;
    }

    public void setItems(List<CreateInvoiceItemRequest> items) {
        this.items = items;
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

    public Invoice.InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Invoice.InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
