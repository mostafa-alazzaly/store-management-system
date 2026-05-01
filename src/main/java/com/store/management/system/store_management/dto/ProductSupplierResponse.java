package com.store.management.system.store_management.dto;

import com.store.management.system.store_management.entity.ProductSupplier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductSupplierResponse {
    private int id ;
    private Integer SupplierId;
    private Integer ProductId;
    private String supplierProductCode ;
    private BigDecimal purchasePrice;
    private Integer minOrderQuantity;
    private Integer leadTimeDays ;
    private boolean isPrimary;
    private ProductSupplier.Status status;
    private LocalDateTime createdAt ;
    private LocalDateTime updatedAt ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(Integer supplierId) {
        SupplierId = supplierId;
    }

    public Integer getProductId() {
        return ProductId;
    }

    public void setProductId(Integer productId) {
        ProductId = productId;
    }

    public String getSupplierProductCode() {
        return supplierProductCode;
    }

    public void setSupplierProductCode(String supplierProductCode) {
        this.supplierProductCode = supplierProductCode;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getMinOrderQuantity() {
        return minOrderQuantity;
    }

    public void setMinOrderQuantity(Integer minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
    }

    public Integer getLeadTimeDays() {
        return leadTimeDays;
    }

    public void setLeadTimeDays(Integer leadTimeDays) {
        this.leadTimeDays = leadTimeDays;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
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

    public ProductSupplier.Status getStatus() {
        return status;
    }

    public void setStatus(ProductSupplier.Status status) {
        this.status = status;
    }
}
