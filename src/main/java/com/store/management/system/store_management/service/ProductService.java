package com.store.management.system.store_management.service;

import com.store.management.system.store_management.dto.product.request.CreateProductRequest;
import com.store.management.system.store_management.dto.product.response.ProductResponse;
import com.store.management.system.store_management.dto.product.request.UpdateProductBarcodeRequest;
import com.store.management.system.store_management.dto.product.request.UpdateProductRequest;
import com.store.management.system.store_management.entity.Category;
import com.store.management.system.store_management.entity.Product;
import com.store.management.system.store_management.entity.ProductSupplier;
import com.store.management.system.store_management.exception.*;
import com.store.management.system.store_management.repo.CategoryRepo;
import com.store.management.system.store_management.repo.ProductRepo;
import com.store.management.system.store_management.repo.ProductSupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final ProductSupplierRepo productSupplierRepo;

    @Autowired
    public ProductService(ProductRepo productRepo,CategoryRepo categoryRepo,ProductSupplierRepo productSupplierRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.productSupplierRepo = productSupplierRepo;
    }

    private ProductResponse maptoProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();

        productResponse.setId(product.getId());
        productResponse.setCategoryId(product.getCategory().getId());
        productResponse.setName(product.getName());
        productResponse.setBrand(product.getBrand());
        productResponse.setProductCode(product.getProductCode());
        productResponse.setDescription(product.getDescription());
        productResponse.setSalePrice(product.getSalePrice());
        productResponse.setStockQuantity(product.getStockQuantity());
        productResponse.setMinStockLevel(product.getMinStockLevel());
        productResponse.setUnit(product.getUnit());
        productResponse.setStatus(product.getStatus());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setBarcode(product.getBarcode());
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }

    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepo.findAll(pageable)
                .map(this::maptoProductResponse);
    }

    public Page<ProductResponse> getAllActiveProducts(Pageable pageable) {
        return productRepo.findByStatus(Product.Status.ACTIVE,pageable)
                .map(this::maptoProductResponse);
    }


    public ProductResponse getProductById(Integer id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isActive = auth != null && auth.getAuthorities()
                                .stream()
                                .anyMatch(a->"ROLE_ADMIN".equals(a.getAuthority()));

        Product product ;
        if (isActive) {
             product = productRepo.findById(id)
                    .orElseThrow (()-> new ProductNotFoundException ("Product not found with id " + id));
        }else{
            product = productRepo.findByIdAndStatus(id,Product.Status.ACTIVE)
                    .orElseThrow (()-> new ProductNotFoundException ("Product not found with id " + id));
        }

        return maptoProductResponse(product);
    }

    @Transactional
    public ProductResponse createProduct (CreateProductRequest request) {
        if (productRepo.existsByBarcode(request.getBarcode())) {
            throw new BarcodeAlreadyExistsException("Barcode already exists");
        }
        Product product = new Product();

        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setSalePrice(request.getSalePrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setMinStockLevel(request.getMinStockLevel());
        product.setUnit(
                request.getUnit()!= null ? request.getUnit() : Product.Unit.KG);
        product.setStatus(
                request.getStatus()!= null? request.getStatus() : Product.Status.ACTIVE);
        product.setImageUrl(request.getImageUrl());
        product.setBarcode(request.getBarcode());
        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id : " +request.getCategoryId()));

        product.setCategory(category);
        Product savedProduct = productRepo.save(product);
        savedProduct.setProductCode(
                "PRO-" + String.format("%03d" , savedProduct.getId())
        );
        return maptoProductResponse(savedProduct);
    }

    private Authentication getAuth() {
        return  SecurityContextHolder.getContext().getAuthentication();
    }
    private boolean hasAnyRole(String... roles) {
        Authentication auth = getAuth();
        if (auth == null) {
            return false;
        }
        return auth.getAuthorities().stream()
                .anyMatch(a -> {
                    String authority = a.getAuthority();
                    for (String role : roles) {
                        if (("ROLE_" + role).equals(authority))
                            return true;
                    }
                    return false;
                });
    }
    private void requireAdminOrManager() {
        if(!hasAnyRole("ADMIN","MANAGER")) {
            throw new  OperationNotAllowedException ("Only admin or manager can perform this action");
        }
    }

    private void  updateName (Product product , UpdateProductRequest request){
        if (request.getName() != null) {
            requireAdminOrManager();
            product.setName(request.getName());
        }
    }
    private void updateBrand (Product product , UpdateProductRequest request){
        if (request.getBrand() != null) {
            requireAdminOrManager();
            product.setBrand(request.getBrand());
        }
    }
    private void updateDescription (Product product , UpdateProductRequest request){
        if (request.getDescription() != null) {
            requireAdminOrManager();
            product.setDescription(request.getDescription());
        }
    }
    private void updateSalePrice (Product product , UpdateProductRequest request){
        if (request.getSalePrice() != null) {
            requireAdminOrManager();
            product.setSalePrice(request.getSalePrice());
        }
    }
    private void updateStockQuantity (Product product , UpdateProductRequest request){
        if (request.getStockQuantity() != null){
            product.setStockQuantity(request.getStockQuantity());
        }
    }
    private void updateMinStockLevel (Product product , UpdateProductRequest request){
        if (request.getMinStockLevel() != null) {
            product.setMinStockLevel(request.getMinStockLevel());
        }
    }
    private void updateUnit(Product product , UpdateProductRequest request){
        if (request.getUnit() != null) {
            requireAdminOrManager();
            product.setUnit(request.getUnit());
        }
    }
    private void updateImageUrl (Product product , UpdateProductRequest request){
        if (request.getImageUrl() != null) {
            product.setImageUrl(request.getImageUrl());
        }
    }
    private void updateCategory (Product product , UpdateProductRequest request){
        Integer categoryId = request.getCategoryId();

        if (categoryId == null ||
                (product.getCategory() != null && product.getCategory().getId() == categoryId)) {
            return;
        }
        requireAdminOrManager();
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id : " + categoryId));

        product.setCategory(category);

    }

    @Transactional
    public ProductResponse updateProduct(Integer id ,  UpdateProductRequest request){

        Product product = productRepo.findById(id)
                        .orElseThrow(()-> new ProductNotFoundException("Product not found with id : " + id));

        updateName(product, request);
        updateBrand(product, request);
        updateDescription(product, request);
        updateSalePrice(product, request);
        updateUnit(product, request);
        updateCategory(product, request);
        updateStockQuantity(product, request);
        updateMinStockLevel(product, request);
        updateImageUrl(product, request);
        productRepo.save(product);
        return maptoProductResponse(product);

    }

    @Transactional
    public void deactivateProduct(Integer id){

        requireAdminOrManager();

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id : " + id));

        if (product.getStatus() == Product.Status.INACTIVE ) {
            throw new ProductAlreadyDeactivatedException("Product with id : " + id + " is already deactivated");
        }
        product.setStatus(Product.Status.INACTIVE);

        if (product.getProductSuppliers() != null) {
            for(ProductSupplier ps : product.getProductSuppliers()){
                ps.setStatus(ProductSupplier.Status.INACTIVE);
                ps.setPrimary(false);
            }
        }
    }

    @Transactional
    public void activateProduct(Integer id){

        requireAdminOrManager();

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id : " + id));

        if (product.getStatus() == Product.Status.ACTIVE) {
            throw new ProductAlreadyActivatedException("Product with id : " + id + " is already activated");
        }

        product.setStatus(Product.Status.ACTIVE);
    }

    @Transactional
    public void updateBarcode (Integer id, UpdateProductBarcodeRequest request){

        requireAdminOrManager();

        String barcode = request.getBarcode();

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id : " + id));

        if (barcode == null || barcode.isBlank()){
            throw new BarcodeCannotBeEmpty ("Barcode cannot be empty");
        }

        if(!product.getBarcode().equals(barcode) &&
                productRepo.existsByBarcode(barcode)){
            throw new BarcodeAlreadyExistsException("Barcode already exists" + barcode);
        }
        product.setBarcode(barcode);
    }


    @Transactional
    public void deleteProduct(Integer id){

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id : " + id));
        if(product.getStatus() == Product.Status.ACTIVE){
            throw new CannotDeleteActiveProductException ("Product with id : " + id + " is already active");
        }
        productSupplierRepo.deleteByProductId(id);
        productRepo.delete(product);
    }
}
