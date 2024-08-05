package com.fc.financecontrolapi.controllers;

import com.fc.financecontrolapi.dtos.expense.ExpenseRequestDTO;
import com.fc.financecontrolapi.exceptions.ResourceNotFoundException;
import com.fc.financecontrolapi.services.interfaces.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ExpenseRequestDTO> addExpense(@Valid @RequestBody ExpenseRequestDTO request) throws ResourceNotFoundException {
        service.addExpense(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
