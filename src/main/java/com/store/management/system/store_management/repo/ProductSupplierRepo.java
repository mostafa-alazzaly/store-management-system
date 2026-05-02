package com.store.management.system.store_management.repo;

import com.store.management.system.store_management.entity.ProductSupplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductSupplierRepo extends JpaRepository<ProductSupplier, Integer> {
    boolean existsBySupplierIdAndProductId(Integer supplierId, Integer productId);
    void deleteBySupplierId(Integer supplierId);
    void deleteByProductId(Integer productId);
    Page<ProductSupplier> findByStatus (ProductSupplier.Status  status, Pageable pageable);
    Optional<ProductSupplier> findByIdAndStatus(Integer productSupplierId, ProductSupplier.Status status);
}
