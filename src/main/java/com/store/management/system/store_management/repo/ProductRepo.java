package com.store.management.system.store_management.repo;

import com.store.management.system.store_management.entity.Category;
import com.store.management.system.store_management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    boolean existsByBarcode (String barcode);
    List<Product> findByStatus (Product.Status status);
    Optional<Product> findByIdAndStatus(Integer id, Product.Status status);
    List<Product> findByCategory(Category category);
}
