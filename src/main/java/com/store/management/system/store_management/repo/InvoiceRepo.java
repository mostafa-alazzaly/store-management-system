package com.store.management.system.store_management.repo;

import com.store.management.system.store_management.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Integer> , JpaSpecificationExecutor<Invoice>
{
    Optional<Invoice> findByIdAndEmployeeId(Integer id, Integer employeeId);
}
