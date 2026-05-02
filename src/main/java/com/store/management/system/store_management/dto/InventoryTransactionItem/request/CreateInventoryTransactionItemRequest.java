package com.store.management.system.store_management.dto.InventoryTransactionItem.request;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;


public class CreateInventoryTransactionItemRequest {

    @NotNull(message="Product Id is required")
    private Integer productId ;

    @NotNull(message="Quantity is required")
    private Integer quantity;

    @NotNull(message="Unit Cost is required")
    @PositiveOrZero(message="Unit Cost must be positive")
    private BigDecimal unitCost;


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }
}
