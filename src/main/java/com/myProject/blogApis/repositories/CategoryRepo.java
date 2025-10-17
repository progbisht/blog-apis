package com.myProject.blogApis.repositories;

import com.myProject.blogApis.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
