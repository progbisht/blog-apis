package com.myProject.blogApis.services;

import com.myProject.blogApis.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    public CategoryDto createCategory(CategoryDto categoryDto);

    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    public void deleteCategory(Integer categoryId);

    public List<CategoryDto> getAllCategories();

    public CategoryDto getCategoryById(Integer categoryId);
}
