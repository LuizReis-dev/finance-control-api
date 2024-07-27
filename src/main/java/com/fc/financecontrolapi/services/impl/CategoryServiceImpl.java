package com.fc.financecontrolapi.services.impl;

import com.fc.financecontrolapi.dtos.category.CategoryDTO;
import com.fc.financecontrolapi.dtos.category.CategoryListDTO;
import com.fc.financecontrolapi.exceptions.ResourceNotFoundException;
import com.fc.financecontrolapi.exceptions.UnprocessableEntityException;
import com.fc.financecontrolapi.exceptions.user.AuthenticationException;
import com.fc.financecontrolapi.model.Category;
import com.fc.financecontrolapi.model.User;
import com.fc.financecontrolapi.repositories.CategoryRepository;
import com.fc.financecontrolapi.services.interfaces.AuthenticationService;
import com.fc.financecontrolapi.services.interfaces.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final AuthenticationService authenticationService;
    private final CategoryRepository repository;

    public CategoryServiceImpl(AuthenticationService authenticationService, CategoryRepository repository) {
        this.authenticationService = authenticationService;
        this.repository = repository;
    }

    @Override
    public void addCategories(CategoryListDTO categoriesDTO) throws AuthenticationException {
        User loggedUser = authenticationService.getAuthenticatedUser();

        List<Category> categories = categoriesDTO.getCategories().stream()
                .map(categoryDTO -> {
                    if (categoryDTO.getName() == null || categoryDTO.getName().trim().isEmpty()) {
                        throw new UnprocessableEntityException("Category", "Category name cannot be empty");
                    }
                    return new Category(categoryDTO.getName(), categoryDTO.getDescription(), loggedUser);
                })
                .collect(Collectors.toList());

        repository.saveAll(categories);
    }

    @Override
    public List<CategoryDTO> getUserActiveCategories() throws AuthenticationException {
        User loggedUser = authenticationService.getAuthenticatedUser();

        return repository.findActiveCategoriesByUser(loggedUser.getId());
    }

    @Override
    public List<CategoryDTO> getUserCategories() throws AuthenticationException {
        User loggedUser = authenticationService.getAuthenticatedUser();
        return repository.findCategoriesByUser(loggedUser.getId());
    }

    @Override
    @Transactional
    public void inactivateCategory(Long categoryId) throws AuthenticationException, ResourceNotFoundException {
        Category category = findCategoryByUser(categoryId);
        category.setIsActive(false);
        repository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) throws AuthenticationException, ResourceNotFoundException {
        Category category = findCategoryByUser(categoryId);
        category.setDeletedAt(Instant.now());
        repository.save(category);
    }

    private Category findCategoryByUser(Long categoryId) throws AuthenticationException, ResourceNotFoundException {
        User loggedUser = authenticationService.getAuthenticatedUser();
        return repository.findCategoryByUserAndCategoryId(loggedUser.getId(), categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this user!"));
    }
}
