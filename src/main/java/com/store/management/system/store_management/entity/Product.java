package com.store.management.system.store_management.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id ;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="brand")
    private String brand;

    @Column(name="product_code")
    private String productCode;

    @Column(name="description")
    private String description;

    @Column(name="sale_price",precision = 10 , scale = 2, nullable=false )
    private BigDecimal salePrice;

    @Column(name="stock_quantity",nullable=false)
    private Integer stockQuantity;

    @Column(name="min_stock_level",nullable=false)
    private Integer minStockLevel;

    @Enumerated(EnumType.STRING)
    @Column(name="unit",nullable=false)
    private Unit unit;

    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable=false)
    private Status status;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="barcode",unique=true,nullable=false)
    private String barcode;

    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy="product")
    private List<ProductSupplier> productSuppliers;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

    public enum Unit{
        PIECE,
        KG,
        LITER,
        BOX,
    }

    public enum Status{
        ACTIVE,
        INACTIVE,
    }
    public void add(ProductSupplier productSupplier){
        if (productSuppliers == null){
            productSuppliers = new ArrayList<>();
        }
        productSuppliers.add(productSupplier);
        productSupplier.setProduct(this);
    }
    public Product (){}

    public Integer getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Category getCategory() {
        return category;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Integer getMinStockLevel() {
        return minStockLevel;
    }

    public void setMinStockLevel(Integer minStockLevel) {
        this.minStockLevel = minStockLevel;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public List<ProductSupplier> getProductSuppliers() {
        return productSuppliers;
    }

    public void setProductSuppliers(List<ProductSupplier> productSuppliers) {
        this.productSuppliers = productSuppliers;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", productCode='" + productCode + '\'' +
                ", description='" + description + '\'' +
                ", salePrice=" + salePrice +
                ", stockQuantity=" + stockQuantity +
                ", unit=" + unit +
                ", minStockLevel=" + minStockLevel +
                ", status=" + status +
                ", imageUrl='" + imageUrl + '\'' +
                ", barcode='" + barcode + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
