package com.fc.financecontrolapi.repositories;

import com.fc.financecontrolapi.dtos.category.CategoryDTO;
import com.fc.financecontrolapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT new com.fc.financecontrolapi.dtos.category.CategoryDTO(obj.name, obj.description, obj.isActive) " +
            "FROM Category obj " +
            "WHERE obj.deletedAt IS NULL AND obj.isActive = TRUE AND obj.user.id =:userId")
    List<CategoryDTO> findActiveCategoriesByUser(Long userId);
}
