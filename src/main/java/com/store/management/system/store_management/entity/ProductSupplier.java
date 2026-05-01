package com.store.management.system.store_management.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="product_supplier")
public class ProductSupplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id ;

    @Column(name="supplier_product_code",length=100)
    private String productSupplierCode ;

    @Column(name="purchase_price", nullable = false, precision = 10 , scale = 2 )
    private BigDecimal purchasePrice;

    @Column(name="min_order_quantity", nullable = false)
    private Integer minOrderQuantity = 1 ;

    @Column(name="lead_time_days")
    private Integer leadTimeDays ;

    @Column(name="is_primary", nullable = false)
    private Boolean primary = false ;

    @Column(name="status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt ;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="supplier_id",nullable=false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id",nullable=false)
    private Product product;

    public enum Status{
        ACTIVE,
        INACTIVE,
    }
    public ProductSupplier(){}

    public int getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getProductSupplierCode() {
        return productSupplierCode;
    }

    public void setProductSupplierCode(String productSupplierCode) {
        this.productSupplierCode = productSupplierCode;
    }

    @Override
    public String toString() {
        return "ProductSupplier{" +
                "id=" + id +
                ", productSupplierCode='" + productSupplierCode + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", minOrderQuantity=" + minOrderQuantity +
                ", leadTimeDays=" + leadTimeDays +
                ", primary=" + primary +
                ", status=" + status +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
