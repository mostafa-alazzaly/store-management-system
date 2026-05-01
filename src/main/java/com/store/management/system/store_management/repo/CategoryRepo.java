package com.store.management.system.store_management.repo;

import com.store.management.system.store_management.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Integer> {
     boolean existsByName(String name);
     List<Category> findCategoryByStatus(Category.Status status);
     Optional<Category> findByIdAndStatus (Integer id,Category.Status status);
}
