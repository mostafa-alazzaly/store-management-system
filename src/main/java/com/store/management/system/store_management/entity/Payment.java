package com.store.management.system.store_management.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="payments")
public class Payment {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="invoice_id",  nullable=false)
    private Invoice  invoice;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="employee_id",  nullable=false)
    private Employee  employee;

    @Column(name="amount", nullable=false)
    private BigDecimal amount;

    @Column(name="payment_date", nullable=false)
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name="payment_method", nullable=false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name="payment_status", nullable=false)
    private PaymentStatus paymentStatus;

    @Column(name="reference_number")
    private String referenceNumber;

    @Column(name="notes")
    private String notes ;

    @CreationTimestamp
    @Column(name="created_at", updatable=false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedDate;

    public enum PaymentStatus {
        PENDING,
        COMPLETED,
        FAILED,
        REFUNDED,
    }

    public enum PaymentMethod {
        CASH,
        CREDIT_CARD,
    }

    public Payment() {}

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Integer getId() {
        return id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public String getReferenceNumber() {
        return referenceNumber;
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

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "amount=" + amount +
                ", id=" + id +
                ", paymentDate=" + paymentDate +
                ", paymentMethod=" + paymentMethod +
                ", paymentStatus=" + paymentStatus +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", notes='" + notes + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}

