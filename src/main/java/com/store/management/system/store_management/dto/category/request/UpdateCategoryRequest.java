package com.store.management.system.store_management.dto.category.request;

import com.store.management.system.store_management.validation.NotBlankIfPresent;

public class UpdateCategoryRequest {


    @NotBlankIfPresent(message="Name can't be empty")
    private String name;
    private String description;
    private Integer parentId;

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
