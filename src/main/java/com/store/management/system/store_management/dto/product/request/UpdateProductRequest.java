package com.store.management.system.store_management.dto.product.request;

import com.store.management.system.store_management.entity.Product;
import com.store.management.system.store_management.validation.NotBlankIfPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class UpdateProductRequest {

    @Positive(message="Category Id must be positive")
    private Integer categoryId;

    @NotBlankIfPresent(message="Category Name is required")
    private String name;

    private String brand;

    private String description;

    @Positive(message="Sale Price must be positive")
    private BigDecimal salePrice;

    @Positive(message="Stock Quantity must be positive")
    private Integer stockQuantity;

    @Positive(message="Min Stock Level must be positive")
    private Integer minStockLevel;

    private Product.Unit unit;

    private String imageUrl;

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public Integer getMinStockLevel() {
        return minStockLevel;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public Product.Unit getUnit() {
        return unit;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
