package com.store.management.system.store_management.repo;

import com.store.management.system.store_management.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
    Role  findByAuthority(String authority);
    Boolean existsByAuthority(String authority);
}
