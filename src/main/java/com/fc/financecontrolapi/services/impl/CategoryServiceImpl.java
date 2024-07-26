package com.fc.financecontrolapi.services.impl;

import com.fc.financecontrolapi.dtos.category.CategoryDTO;
import com.fc.financecontrolapi.dtos.category.CategoryListDTO;
import com.fc.financecontrolapi.exceptions.user.AuthenticationException;
import com.fc.financecontrolapi.model.Category;
import com.fc.financecontrolapi.model.User;
import com.fc.financecontrolapi.repositories.CategoryRepository;
import com.fc.financecontrolapi.services.interfaces.AuthenticationService;
import com.fc.financecontrolapi.services.interfaces.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
                .map(categoryDTO -> new Category(categoryDTO.getName(), categoryDTO.getDescription(), loggedUser))
                .collect(Collectors.toList());

        repository.saveAll(categories);
    }
}