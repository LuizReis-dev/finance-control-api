package com.fc.financecontrolapi.services.impl;

import com.fc.financecontrolapi.dtos.expense.ExpenseRequestDTO;
import com.fc.financecontrolapi.exceptions.ResourceNotFoundException;
import com.fc.financecontrolapi.exceptions.UnprocessableEntityException;
import com.fc.financecontrolapi.model.Category;
import com.fc.financecontrolapi.model.Expense;
import com.fc.financecontrolapi.model.PaymentMethod;
import com.fc.financecontrolapi.repositories.ExpenseRepository;
import com.fc.financecontrolapi.services.interfaces.CategoryService;
import com.fc.financecontrolapi.services.interfaces.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpenseServiceImpl.class);
    private final ExpenseRepository repository;
    private final CategoryService categoryService;

    public ExpenseServiceImpl(ExpenseRepository repository, CategoryService categoryService) {
        this.repository = repository;
        this.categoryService = categoryService;
    }

    @Override
    public void addExpense(ExpenseRequestDTO request) throws ResourceNotFoundException {
        Map<PaymentMethod, Consumer<ExpenseRequestDTO>> strategies = new HashMap<>();
        strategies.put(PaymentMethod.CASH, this::handleNonInstallmentExpense);
        strategies.put(PaymentMethod.DEBITCARD, this::handleNonInstallmentExpense);
        strategies.put(PaymentMethod.PIX, this::handleNonInstallmentExpense);
        strategies.put(PaymentMethod.CREDITCARD, this::handleInstallmentExpense);

        if (!PaymentMethod.isValid(request.getPaymentMethod().toUpperCase()))
            throw new UnprocessableEntityException("PaymentMethod", "Invalid payment method");

        categoryService.findCategoryByUser(request.getCategoryId());
        PaymentMethod paymentMethod = PaymentMethod.valueOf(request.getPaymentMethod().toUpperCase());
        Consumer<ExpenseRequestDTO> selectedStrategy = strategies.get(paymentMethod);
        selectedStrategy.accept(request);
    }
    private void handleNonInstallmentExpense(ExpenseRequestDTO request) {
        PaymentMethod paymentMethod = PaymentMethod.valueOf(request.getPaymentMethod().toUpperCase());

        Expense expense = new Expense(new Category(request.getCategoryId()), request.getAmount(), paymentMethod, request.getDescription());

        expense.setPaymentDate(Instant.now());
        repository.save(expense);
    }

    private void handleInstallmentExpense(ExpenseRequestDTO request) {
        validateInstallmentExpense(request);
        PaymentMethod paymentMethod = PaymentMethod.valueOf(request.getPaymentMethod().toUpperCase());

        List<Expense> expenses = new ArrayList<>();

        int initialMonth = request.getInitialMonth();
        int currentYear = LocalDateTime.now().getYear();

        Integer installments = request.getInstallments();
        Integer installmentAmount = request.getAmount() / installments;

        for(int i = 0; i < installments; i++) {

            LocalDateTime localDateTime = LocalDateTime.of(currentYear, initialMonth, 1, 0, 0);
            localDateTime = localDateTime.plusMonths(i);
            Instant paymentDate = localDateTime.toInstant(ZoneOffset.UTC);

            Expense expense = new Expense(new Category(request.getCategoryId()), installmentAmount, paymentMethod, request.getDescription());

            expense.setPaymentDate(paymentDate);
            expenses.add(expense);
        }

        repository.saveAll(expenses);
    }

    private void validateInstallmentExpense(ExpenseRequestDTO request){
        if(request.getInstallments() == null)
            throw new UnprocessableEntityException("Installments", "No installments");

        if(request.getInitialMonth() == null)
            throw new UnprocessableEntityException("Initial Month", "No initial month");

        if(!(request.getInitialMonth() >= 1 && request.getInitialMonth() <= 12))
            throw new UnprocessableEntityException("InitialMonth", "Invalid month");

        if(request.getInstallments() <= 0)
            throw new UnprocessableEntityException("Installments", "Invalid installments");
    }

}
