package com.store.management.system.store_management.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id ;

    @OneToOne(mappedBy="invoice")
    private InventoryTransaction inventoryTransaction;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="employee_id", nullable=false)
    private Employee employee;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="supplier_id")
    private Supplier supplier;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToMany(mappedBy="invoice",fetch=FetchType.LAZY ,  cascade=CascadeType.ALL , orphanRemoval=true)
    private List<InvoiceItem> invoiceItems = new ArrayList<>();

    @Column(name="invoice_code", unique=true)
    private String invoiceCode;

    @Column(name="invoice_date", nullable=false)
    private LocalDate invoiceDate;

    @Column(name="due_date")
    private LocalDate dueDate;

    @Column(name="total_amount" ,nullable=false)
    private BigDecimal totalAmount;

    @Column(name="discount_amount", nullable=false)
    private BigDecimal discountAmount;

    @Column(name="final_amount", nullable=false)
    private BigDecimal finalAmount;

    @Column(name="paid_amount", nullable=false)
    private BigDecimal paidAmount;

    @Column(name="remaining_amount", nullable=false)
    private BigDecimal remainingAmount;

    @Enumerated(EnumType.STRING)
    @Column(name="invoice_type", nullable=false)
    private InvoiceType invoiceType;

    @Enumerated(EnumType.STRING)
    @Column(name="payment_status", nullable=false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name="invoice_status", nullable=false)
    private InvoiceStatus invoiceStatus;

    @Column(name="notes")
    private String notes ;

    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    @CreationTimestamp
    @Column(name="created_at", updatable=false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedDate;

    public InventoryTransaction getInventoryTransaction() {
        return inventoryTransaction;
    }

    public void setInventoryTransaction(InventoryTransaction inventoryTransaction) {
        this.inventoryTransaction = inventoryTransaction;
    }

    public void addInvoiceItem(InvoiceItem invoiceItem) {
        invoiceItems.add(invoiceItem);
        invoiceItem.setInvoice(this);
    }
    public  enum InvoiceStatus {
        DRAFT,
        POSTED,
        CANCELED,
    }

    public enum PaymentStatus {
        UNPAID,
        PARTIALLY_PAID,
        PAID,
    }

    public enum InvoiceType {
        SALE,
        PURCHASE,
    }

    public Invoice (){}

    public Integer getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public InvoiceType getInvoiceType() {
        return invoiceType;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", invoiceCode='" + invoiceCode + '\'' +
                ", invoiceDate=" + invoiceDate +
                ", dueDate=" + dueDate +
                ", totalAmount=" + totalAmount +
                ", discountAmount=" + discountAmount +
                ", finalAmount=" + finalAmount +
                ", paidAmount=" + paidAmount +
                ", remainingAmount=" + remainingAmount +
                ", invoiceType=" + invoiceType +
                ", paymentStatus=" + paymentStatus +
                ", invoiceStatus=" + invoiceStatus +
                ", notes='" + notes + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
