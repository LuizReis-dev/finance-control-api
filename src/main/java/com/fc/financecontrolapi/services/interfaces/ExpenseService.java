package com.fc.financecontrolapi.services.interfaces;

import com.fc.financecontrolapi.dtos.expense.ExpenseRequestDTO;
import com.fc.financecontrolapi.exceptions.ResourceNotFoundException;

public interface ExpenseService {

    void addExpense(ExpenseRequestDTO request) throws ResourceNotFoundException;
}
