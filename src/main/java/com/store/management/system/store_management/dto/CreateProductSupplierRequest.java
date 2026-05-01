package com.store.management.system.store_management.dto;

import com.store.management.system.store_management.entity.ProductSupplier;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreateProductSupplierRequest {

    @NotNull(message="Supplier Id is required")
    private Integer supplierId;

    @NotNull(message="Product Id is required")
    private Integer productId;

    @NotNull(message="Purchase Price is required")
    @DecimalMin(value = "0.0",inclusive = false,message="Purchase Price Must be Positive")
    private BigDecimal purchasePrice;

    @NotNull(message="Min Order Quantity is required")
    private Integer minOrderQuantity;

    private Integer leadTimeDays ;

    @NotNull(message="You must specify whether this supplier is primary")
    private Boolean primary;

    private ProductSupplier.Status status;

    public Integer getSupplierId() {
        return supplierId;
    }

    public Integer getProductId() {
        return productId;
    }

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

    public ProductSupplier.Status getStatus() {
        return status;
    }

    public void setStatus(ProductSupplier.Status status) {
        this.status = status;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setMinOrderQuantity(Integer minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
    }

    public void setLeadTimeDays(Integer leadTimeDays) {
        this.leadTimeDays = leadTimeDays;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }
}
