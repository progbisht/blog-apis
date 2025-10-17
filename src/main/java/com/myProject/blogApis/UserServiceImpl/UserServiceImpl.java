package com.myProject.blogApis.UserServiceImpl;

import com.myProject.blogApis.exceptions.ResourceNotFoundException;
import com.myProject.blogApis.models.User;
import com.myProject.blogApis.payloads.UserDto;
import com.myProject.blogApis.repositories.UserRepo;
import com.myProject.blogApis.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);

        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = this.userRepo.save(user);

        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));

        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();

        return users.stream().map(this::userToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

        this.userRepo.delete(user);

    }

    public User dtoToUser(UserDto userDto){
        return this.modelMapper.map(userDto, User.class);
    }

    public UserDto userToDto(User user){
        return this.modelMapper.map(user, UserDto.class);
    }
}
