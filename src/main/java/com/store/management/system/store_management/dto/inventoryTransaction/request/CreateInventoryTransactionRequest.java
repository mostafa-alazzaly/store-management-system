package com.store.management.system.store_management.dto.inventoryTransaction.request;

import com.store.management.system.store_management.dto.InventoryTransactionItem.request.CreateInventoryTransactionItemRequest;
import com.store.management.system.store_management.entity.InventoryTransaction;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.List;

public class CreateInventoryTransactionRequest {


    @NotNull(message= "Employee Id is required")
    private Integer employeeId;

    private Integer supplierId;

    @NotNull(message="Transaction Type is required")
    private InventoryTransaction.TransactionType transactionType;

    @NotNull(message="Transaction Date is required")
    @PastOrPresent(message = "Transaction date must be in the past or present ")
    private LocalDate transactionDate;

    private String notes ;

    @Valid
    @NotEmpty(message="Transactions items are required")
    private List<CreateInventoryTransactionItemRequest> items ;

    public List<CreateInventoryTransactionItemRequest> getItems() {
        return items;
    }

    public void setItems(List<CreateInventoryTransactionItemRequest> items) {
        this.items = items;
    }

    public CreateInventoryTransactionRequest() {}

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public InventoryTransaction.TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(InventoryTransaction.TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }
}
