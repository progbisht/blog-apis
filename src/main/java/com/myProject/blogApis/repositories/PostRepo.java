package com.myProject.blogApis.repositories;

import com.myProject.blogApis.models.Category;
import com.myProject.blogApis.models.Post;
import com.myProject.blogApis.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);
}
