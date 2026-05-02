package com.store.management.system.store_management.repo;

import com.store.management.system.store_management.entity.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryTransactionRepo extends JpaRepository<InventoryTransaction, Integer> {

    boolean existsByOriginalTransactionId(Integer originalTransactionId);
}
