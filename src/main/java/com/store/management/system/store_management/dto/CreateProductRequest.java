package com.store.management.system.store_management.dto;

import com.store.management.system.store_management.entity.Product;
import com.store.management.system.store_management.validation.NotNullString;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CreateProductRequest {

    @NotNull(message="Category Id is required")
    private Integer categoryId;

    @NotNullString(message="Name can't be 'null'")
    @NotBlank(message="Name is required")
    private String name;

    private String brand;

    private String description;

    @NotNull(message="Sale Price is required ")
    @DecimalMin(value= "0.0",inclusive = false,message="Sale Price must be positive ")
    private BigDecimal salePrice;

    @Positive(message="Stock Quantity must be positive")
    @NotNull(message="Stock Quantity is required")
    private Integer stockQuantity;

    @Positive(message="Min stock Level must be positive")
    @NotNull(message="Min Stock Level is required")
    private Integer minStockLevel;

    private Product.Unit unit;
    private Product.Status status;

    private String imageUrl;

    @NotNullString(message="Barcode can't be 'null'")
    @NotBlank(message="Barcode is required ")
    private String barcode;

    public CreateProductRequest() {}

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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public Product.Unit getUnit() {
        return unit;
    }

    public void setUnit(Product.Unit unit) {
        this.unit = unit;
    }

    public Product.Status getStatus() {
        return status;
    }

    public void setStatus(Product.Status status) {
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
}
