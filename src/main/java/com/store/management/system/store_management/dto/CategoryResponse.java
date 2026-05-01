package com.store.management.system.store_management.dto;

import com.store.management.system.store_management.entity.Category;

import java.time.LocalDateTime;

public class CategoryResponse {

    private int id ;
    private String name;
    private String description;
    private Integer ParentCategoryId;
    private String ParentCategoryName;
    private Category.Status status;
    private LocalDateTime createdAt ;
    private LocalDateTime updatedAt ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category.Status getStatus() {
        return status;
    }

    public void setStatus(Category.Status status) {
        this.status = status;
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

    public String getParentCategoryName() {
        return ParentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        ParentCategoryName = parentCategoryName;
    }

    public Integer getParentCategoryId() {
        return ParentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        ParentCategoryId = parentCategoryId;
    }
}
