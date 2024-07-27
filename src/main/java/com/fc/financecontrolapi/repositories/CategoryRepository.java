package com.fc.financecontrolapi.repositories;

import com.fc.financecontrolapi.dtos.category.CategoryDTO;
import com.fc.financecontrolapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT new com.fc.financecontrolapi.dtos.category.CategoryDTO(obj.id, obj.name, obj.description, obj.isActive) " +
            "FROM Category obj " +
            "WHERE obj.deletedAt IS NULL AND obj.isActive = TRUE AND obj.user.id =:userId")
    List<CategoryDTO> findActiveCategoriesByUser(Long userId);

    @Query("SELECT new com.fc.financecontrolapi.dtos.category.CategoryDTO(obj.id, obj.name, obj.description, obj.isActive) " +
            "FROM Category obj " +
            "WHERE obj.deletedAt IS NULL AND obj.user.id =:userId")
    List<CategoryDTO> findCategoriesByUser(Long userId);

    @Query("SELECT obj FROM Category obj " +
            "WHERE obj.deletedAt IS NULL AND obj.user.id = :userId AND obj.id = :categoryId")
    Optional<Category> findCategoryByUserAndCategoryId(Long userId, Long categoryId);

}
