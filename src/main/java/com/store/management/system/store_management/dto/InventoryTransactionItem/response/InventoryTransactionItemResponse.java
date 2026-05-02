package com.store.management.system.store_management.dto.InventoryTransactionItem.response;

import java.math.BigDecimal;

public class InventoryTransactionItemResponse {

    private Integer id;
    private Integer productId ;
    private Integer inventoryTransactionId;
    private Integer quantity;
    private BigDecimal unitCost;

    public InventoryTransactionItemResponse (){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getInventoryTransactionId() {
        return inventoryTransactionId;
    }

    public void setInventoryTransactionId(Integer inventoryTransactionId) {
        this.inventoryTransactionId = inventoryTransactionId;
    }
}
