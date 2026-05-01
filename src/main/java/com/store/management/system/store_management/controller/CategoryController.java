package com.store.management.system.store_management.controller;

import com.store.management.system.store_management.dto.CategoryResponse;
import com.store.management.system.store_management.dto.CreateCategoryRequest;
import com.store.management.system.store_management.dto.UpdateCategoryRequest;
import com.store.management.system.store_management.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public List<CategoryResponse> getAllCategories(){
        return  categoryService.getAllCategories();
    }
    @GetMapping
    public List<CategoryResponse> getAllActiveCategories(){
        return  categoryService.getAllActiveCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Integer id){
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public CategoryResponse createCategory( @Valid @RequestBody CreateCategoryRequest request){
        return categoryService.createCategory(request);
    }

    @PatchMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Integer id, @Valid @RequestBody UpdateCategoryRequest request){
        return categoryService.updateCategory(id,request);
    }

    @PatchMapping("/{id}/deactivate")
    public String deactivateCategory(@PathVariable Integer id){
        categoryService.deactivateCategory(id);

        return " Category deactivated with id : " + id ;
    }
    @PatchMapping("/{id}/activate")
    public String activateCategory(@PathVariable Integer id){
        categoryService.activateCategory(id);
        return " Category activated with id : " + id ;
    }

    @DeleteMapping("/{id}")
    public String deleteCategoryById(@PathVariable Integer id){
        categoryService.deleteCategoryById(id);
        return "Category deleted successfully :" + id ;
    }
}
