package com.store.management.system.store_management.repo;

import com.store.management.system.store_management.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier, Integer> {

    Boolean existsByEmail(String email);
    Boolean existsByTaxNumber(String taxCode);
    Page<Supplier> findByStatus(Supplier.Status status, Pageable pageable);
    Optional<Supplier> findByIdAndStatus(Integer id, Supplier.Status status);
}
