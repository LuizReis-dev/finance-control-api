package com.fc.financecontrolapi.services.interfaces;

import com.fc.financecontrolapi.dtos.category.CategoryListDTO;
import com.fc.financecontrolapi.exceptions.user.AuthenticationException;

import java.util.List;

public interface CategoryService {

    void addCategories(CategoryListDTO categories) throws AuthenticationException;
}
