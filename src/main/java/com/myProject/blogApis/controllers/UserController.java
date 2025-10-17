package com.myProject.blogApis.controllers;

import com.myProject.blogApis.payloads.ApiResponse;
import com.myProject.blogApis.payloads.UserDto;
import com.myProject.blogApis.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createdUserDto = this.userService.createUser(userDto);

        return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId){
        UserDto updatedUser = this.userService.updateUser(userDto, userId);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId){
        this.userService.deleteUser(userId);

        return new ResponseEntity<>(new ApiResponse("User deleted successfully.", true), HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(this.userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
        UserDto userDto = this.userService.getUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
