package com.store.management.system.store_management.repo;

import com.store.management.system.store_management.entity.InventoryTransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryTransactionItemRepo  extends JpaRepository<InventoryTransactionItem,Integer>
{
}
