package com.store.management.system.store_management.repo;

import com.store.management.system.store_management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer> {
    Boolean  existsByUser_Id(Integer userId);
}
