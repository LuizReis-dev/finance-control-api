package com.fc.financecontrolapi.controllers;

import com.fc.financecontrolapi.dtos.category.CategoryListDTO;
import com.fc.financecontrolapi.exceptions.user.AuthenticationException;
import com.fc.financecontrolapi.services.interfaces.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> addCategories(@RequestBody @Valid CategoryListDTO categoriesDTO) throws AuthenticationException {
        service.addCategories(categoriesDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
