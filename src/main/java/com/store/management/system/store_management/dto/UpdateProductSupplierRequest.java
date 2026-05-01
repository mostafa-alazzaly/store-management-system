package com.store.management.system.store_management.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class UpdateProductSupplierRequest {


    @Positive(message="Purchase Price must be positive")
    private BigDecimal purchasePrice;

    @Positive(message="Min Order Quantity  must be positive")
    private Integer minOrderQuantity;

    @Positive(message="Lead Time days must be positive")
    private Integer leadTimeDays ;

    private Boolean primary;

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public Integer getMinOrderQuantity() {
        return minOrderQuantity;
    }

    public Integer getLeadTimeDays() {
        return leadTimeDays;
    }

    public Boolean getPrimary() {
        return primary;
    }
}
