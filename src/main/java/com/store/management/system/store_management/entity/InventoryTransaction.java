package com.store.management.system.store_management.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="inventory_transactions")
public class InventoryTransaction {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="employee_id", nullable=false)
    private Employee employee;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="supplier_id")
    private Supplier supplier;

    @Enumerated(EnumType.STRING)
    @Column(name="transaction_type", nullable=false)
    private TransactionType  transactionType;

    @Column(name="transaction_date", nullable=false)
    private LocalDate transactionDate;

    @Column(name="notes")
    private String notes ;

    @CreationTimestamp
    @Column(name="created_at", updatable=false)
    private LocalDateTime  createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime  updatedAt;

    @OneToMany(mappedBy="inventoryTransaction",
                cascade = CascadeType.ALL,
                orphanRemoval  = true)
    private List<InventoryTransactionItem> inventoryTransactionItems = new ArrayList<>();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn (name="original_transaction_id")
    private InventoryTransaction  originalTransaction ;

    public void addInventoryTransactionItem (InventoryTransactionItem inventoryTransactionItem) {
        inventoryTransactionItems.add(inventoryTransactionItem);
        inventoryTransactionItem.setInventoryTransaction(this);
    }

    public enum TransactionType {
        IN,
        OUT,
        CUSTOMER_RETURN,
        SUPPLIER_RETURN,
        ADJUSTMENT,
        DAMAGE,
    }

    public InventoryTransaction() {
    }

    public InventoryTransaction getOriginalTransaction() {
        return originalTransaction;
    }

    public void setOriginalTransaction(InventoryTransaction originalTransaction) {
        this.originalTransaction = originalTransaction;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<InventoryTransactionItem> getInventoryTransactionItems() {
        return inventoryTransactionItems;
    }

    public void setInventoryTransactionItems(List<InventoryTransactionItem> inventoryTransactionItems) {
        this.inventoryTransactionItems = inventoryTransactionItems;
    }

    @Override
    public String toString() {
        return "InventoryTransaction{" +
                "id=" + id +
                ", transactionType=" + transactionType +
                ", notes='" + notes + '\'' +
                ", transactionDate=" + transactionDate +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
