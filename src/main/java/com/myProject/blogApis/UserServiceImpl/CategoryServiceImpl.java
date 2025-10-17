package com.myProject.blogApis.UserServiceImpl;

import com.myProject.blogApis.exceptions.ResourceNotFoundException;
import com.myProject.blogApis.models.Category;
import com.myProject.blogApis.payloads.CategoryDto;
import com.myProject.blogApis.repositories.CategoryRepo;
import com.myProject.blogApis.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category savedCategory = this.categoryRepo.save(category);
        return this.modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedCategory = this.categoryRepo.save(category);
        return this.modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Categoryy Id", categoryId));

        this.categoryRepo.delete(category);

    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.categoryRepo.findAll();

        return categories.stream().map(category -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));

        return this.modelMapper.map(category, CategoryDto.class);
    }
}
