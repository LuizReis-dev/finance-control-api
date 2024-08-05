package com.fc.financecontrolapi.services.interfaces;

import com.fc.financecontrolapi.dtos.category.CategoryDTO;
import com.fc.financecontrolapi.dtos.category.CategoryListDTO;
import com.fc.financecontrolapi.exceptions.ResourceNotFoundException;
import com.fc.financecontrolapi.exceptions.user.AuthenticationException;
import com.fc.financecontrolapi.model.Category;

import java.util.List;

public interface CategoryService {

    void addCategories(CategoryListDTO categories);
    List<CategoryDTO> getUserActiveCategories();
    List<CategoryDTO> getUserCategories();
    void inactivateCategory(Long categoryId)throws ResourceNotFoundException;
    void activateCategory(Long categoryId)throws ResourceNotFoundException;
    void deleteCategory(Long categoryId) throws ResourceNotFoundException;
    Category findCategoryByUser(Long categoryId) throws ResourceNotFoundException;
}
