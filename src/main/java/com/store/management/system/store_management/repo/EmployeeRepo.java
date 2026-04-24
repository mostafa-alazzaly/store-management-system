package com.store.management.system.store_management.repo;

import com.store.management.system.store_management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    Boolean existsByUser_Id(Integer userId);
}
