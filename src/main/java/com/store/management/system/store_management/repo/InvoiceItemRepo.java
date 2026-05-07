package com.store.management.system.store_management.repo;

import com.store.management.system.store_management.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemRepo extends JpaRepository<InvoiceItem, Integer>, JpaSpecificationExecutor<InvoiceItem> {
}
