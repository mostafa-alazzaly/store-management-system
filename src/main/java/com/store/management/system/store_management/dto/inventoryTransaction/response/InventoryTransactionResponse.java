package com.store.management.system.store_management.dto.inventoryTransaction.response;

import com.store.management.system.store_management.dto.InventoryTransactionItem.response.InventoryTransactionItemResponse;
import com.store.management.system.store_management.entity.InventoryTransaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class InventoryTransactionResponse {
    private int id;
    private Integer employeeId;
    private Integer supplierId;
    private InventoryTransaction.TransactionType transactionType;
    private LocalDate transactionDate;
    private String notes ;
    private LocalDateTime createdAt;
    private LocalDateTime  updatedAt;
    private List<InventoryTransactionItemResponse> items ;
    private String message;
    private List<String> warnings;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public InventoryTransactionResponse() {}

    public List<InventoryTransactionItemResponse> getItems() {
        return items;
    }

    public void setItems(List<InventoryTransactionItemResponse> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public InventoryTransaction.TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(InventoryTransaction.TransactionType transactionType) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
