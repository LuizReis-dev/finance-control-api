package com.fc.financecontrolapi.controllers;

import com.fc.financecontrolapi.dtos.category.CategoryDTO;
import com.fc.financecontrolapi.dtos.category.CategoryListDTO;
import com.fc.financecontrolapi.exceptions.ResourceNotFoundException;
import com.fc.financecontrolapi.exceptions.user.AuthenticationException;
import com.fc.financecontrolapi.services.interfaces.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = "/user/actives")
    public ResponseEntity<List<CategoryDTO>> getUserActiveCategories() throws AuthenticationException {
        return ResponseEntity.ok(service.getUserActiveCategories());
    }

    @GetMapping(value = "/user")
    public ResponseEntity<List<CategoryDTO>> getUserCategories() throws AuthenticationException {
        return ResponseEntity.ok(service.getUserCategories());
    }

    @PutMapping(value = "/inactivate/{categoryId}")
    public ResponseEntity<Void> inactivateCategory(@PathVariable Long categoryId) throws AuthenticationException, ResourceNotFoundException {
        service.inactivateCategory(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
