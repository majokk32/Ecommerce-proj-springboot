package com.ecommerce.sbproject.service;

import com.ecommerce.sbproject.model.Category;
import com.ecommerce.sbproject.payload.CategoryDTO;
import com.ecommerce.sbproject.payload.CategoryResponse;


public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryID);
    CategoryDTO update(CategoryDTO category, Long categoryID);
    CategoryResponse getAllCategories(Integer pageNumber,Integer pageSize, String sortBy, String sortOrder);
}
