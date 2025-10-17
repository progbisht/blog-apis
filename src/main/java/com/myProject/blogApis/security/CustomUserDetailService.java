package com.myProject.blogApis.security;

import com.myProject.blogApis.exceptions.ResourceNotFoundException;
import com.myProject.blogApis.models.User;
import com.myProject.blogApis.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User", "email"+username, 0));

        return User;
    }
}
