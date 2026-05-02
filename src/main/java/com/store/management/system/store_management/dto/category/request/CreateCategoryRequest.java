package com.store.management.system.store_management.dto.category.request;

import com.store.management.system.store_management.entity.Category;
import com.store.management.system.store_management.validation.NotNullString;
import jakarta.validation.constraints.NotBlank;

public class CreateCategoryRequest {

    @NotNullString(message="Name can't be 'null'")
    @NotBlank(message="Name is required")
    private String name;
    private String description;
    private Category.Status status;
    private Integer parentId;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category.Status getStatus() {
        return status;
    }

    public Integer getParentId() {
        return parentId;
    }
}
