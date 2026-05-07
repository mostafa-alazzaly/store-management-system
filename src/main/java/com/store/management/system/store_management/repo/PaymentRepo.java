package com.store.management.system.store_management.repo;

import com.store.management.system.store_management.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo  extends JpaRepository<Payment, Integer> , JpaSpecificationExecutor<Payment> {

    boolean existsByReferenceNumber(String referenceNumber);
}
