package com.myProject.blogApis.controllers;

import com.myProject.blogApis.payloads.ApiResponse;
import com.myProject.blogApis.payloads.CategoryDto;
import com.myProject.blogApis.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createCategory = this.categoryService.createCategory(categoryDto);

        return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, categoryId);

        return new ResponseEntity<CategoryDto>(updateCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);

        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully.", true), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return new ResponseEntity<List<CategoryDto>>(this.categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId){
        CategoryDto singleCategory = this.categoryService.getCategoryById(categoryId);
        return new ResponseEntity<CategoryDto>(singleCategory, HttpStatus.OK);
    }


}
