package com.myProject.blogApis.repositories;

import com.myProject.blogApis.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}

