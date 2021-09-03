package com.spring.ecommerce.service;

import com.spring.ecommerce.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    public void addCategory(Category category);

    public List<Category> getAllCategory();

    public Category getcategoryById(Long id);

    public void updateCategory(Category category);

    public void deleteCategory(Long id);
}
