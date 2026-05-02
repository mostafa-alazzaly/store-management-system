package com.store.management.system.store_management.repo;

import com.store.management.system.store_management.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Integer> {
     boolean existsByName(String name);
     Page<Category> findCategoryByStatus(Category.Status status, Pageable pageable);
     Optional<Category> findByIdAndStatus (Integer id,Category.Status status);
}
