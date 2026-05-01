package com.store.management.system.store_management.service;

import com.store.management.system.store_management.dto.CategoryResponse;
import com.store.management.system.store_management.dto.CreateCategoryRequest;
import com.store.management.system.store_management.dto.UpdateCategoryRequest;
import com.store.management.system.store_management.entity.Category;
import com.store.management.system.store_management.entity.Product;
import com.store.management.system.store_management.exception.*;
import com.store.management.system.store_management.repo.CategoryRepo;
import com.store.management.system.store_management.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {
     private final CategoryRepo categoryRepo;
     private final ProductRepo productRepo;

     @Autowired
     public CategoryService(CategoryRepo categoryRepo, ProductRepo productRepo) {
         this.categoryRepo = categoryRepo;
         this.productRepo = productRepo;
     }

     public CategoryResponse mapToResponse(Category category){
         CategoryResponse categoryResponse = new CategoryResponse();
         categoryResponse.setId(category.getId());
         categoryResponse.setName(category.getName());
         categoryResponse.setDescription(category.getDescription());
         categoryResponse.setStatus(category.getStatus());
         categoryResponse.setCreatedAt(category.getCreatedAt());
         categoryResponse.setUpdatedAt(category.getUpdatedAt());
         if (category.getParent() != null) {
             categoryResponse.setParentCategoryId(category.getParent().getId());
             categoryResponse.setParentCategoryName(category.getParent().getName());
         }
         return categoryResponse;
     }
     public List<CategoryResponse> getAllCategories(){
         return categoryRepo.findAll()
                 .stream()
                 .map(this::mapToResponse)
                 .toList();
     }

     public List<CategoryResponse> getAllActiveCategories(){
         return categoryRepo.findCategoryByStatus(Category.Status.ACTIVE)
                 .stream()
                 .map(this::mapToResponse)
                 .toList();
     }

     public CategoryResponse getCategoryById(Integer id){
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         boolean isAdmin  = auth != null && auth.getAuthorities()
                 .stream()
                 .anyMatch(a->"ROLE_ADMIN".equals(a.getAuthority()));

         Category category;
         if (isAdmin){
             category = categoryRepo.findById(id)
                     .orElseThrow(()-> new CategoryNotFoundException("Category Not Found with id : " + id) );
         }else
         {
             category = categoryRepo.findByIdAndStatus(id,Category.Status.ACTIVE)
                     .orElseThrow(()-> new CategoryNotFoundException("Category Not Found with id : " + id));
         }
         return mapToResponse(category);
     }

     public CategoryResponse createCategory(CreateCategoryRequest request){
         Category category = new Category();

         if(categoryRepo.existsByName(request.getName())){
             throw new CategoryNameAlreadyExistsException("Category Name already Exists : " + request.getName());
         }

         if (request.getParentId() != null) {
             Category parent = categoryRepo.findById(request.getParentId())
                     .orElseThrow (()->new ParentNotFoundException("Parent Not Found" ));
             category.setParent(parent);
         }

         category.setName(request.getName());
         category.setDescription(request.getDescription());
         category.setStatus(
                 request.getStatus() !=null ? request.getStatus():Category.Status.ACTIVE );

         Category savedCategory = categoryRepo.save(category);
         return mapToResponse(savedCategory);
     }

     private void updateName(UpdateCategoryRequest request, Category category){
         if(request.getName() != null){

             if( !request.getName().equals(category.getName()) &&
                     categoryRepo.existsByName(request.getName())){
                 throw new CategoryNameAlreadyExistsException("Category Name already Exists : " + request.getName());
             }
             category.setName(request.getName());
         }
     }
     private void updateDescription(UpdateCategoryRequest request, Category category){
         if(request.getDescription() != null){
             category.setDescription(request.getDescription());
         }
     }
     private void updateCategoryParent(UpdateCategoryRequest request, Category category){
         Integer parentId = request.getParentId();

         if(Objects.equals(category.getId(),parentId)){
             throw new CategoryCannotBeParentOfItselfException("Category cannot be parent of itself");
         }

         if (parentId == null ||
                 (category.getParent() != null && Objects.equals(category.getParent().getId(),parentId))){
             return  ;
         }
         Category category2 = categoryRepo.findById(request.getParentId())
                 .orElseThrow(()->new CategoryNotFoundException("Category Not Found with id : " + request.getParentId()));
         category.setParent(category2);
     }
     @Transactional
     public CategoryResponse updateCategory(Integer id , UpdateCategoryRequest request){
         Category category = categoryRepo.findById(id)
                 .orElseThrow(()->new CategoryNotFoundException("Category Not Found with id : " + id));

         updateName(request, category);
         updateDescription(request, category);
         updateCategoryParent(request, category);

         categoryRepo.save(category);
         return mapToResponse(category);
     }

     @Transactional
     public void deactivateCategory(Integer id){

         Category category = categoryRepo.findById(id)
                 .orElseThrow(()->new CategoryNotFoundException("Category Not Found with id : " + id));

         if (category.getStatus() == Category.Status.INACTIVE){
             throw new CategoryAlreadyDeactivatedException("Category with id : " + id + " is already deactivated");
         }

         category.setStatus(Category.Status.INACTIVE);

         if(category.getProducts() != null){
             for (Product product : category.getProducts()) {
                 product.setStatus(Product.Status.INACTIVE);
             }
         }

     }

    @Transactional
    public void activateCategory(Integer id){

        Category category = categoryRepo.findById(id)
                .orElseThrow(()->new CategoryNotFoundException("Category Not Found with id : " + id));

        if (category.getStatus() == Category.Status.ACTIVE){
            throw new CategoryAlreadyActivatedException("Category with id : " + id + " is already activated");
        }

        category.setStatus(Category.Status.ACTIVE);
    }


     @Transactional
     public void deleteCategoryById(Integer id){

         Category category = categoryRepo.findById(id)
                 .orElseThrow(()->new CategoryNotFoundException("Category Not Found with id : " + id));

        List<Product> products = productRepo.findByCategory(category);
        for (Product product :  products ) {
            product.setCategory(null);
        }
        categoryRepo.delete(category);
     }

}
