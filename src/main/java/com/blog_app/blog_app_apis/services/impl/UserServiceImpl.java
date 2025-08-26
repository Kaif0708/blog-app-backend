package com.blog_app.blog_app_apis.services.impl;

import com.blog_app.blog_app_apis.entities.User;
import com.blog_app.blog_app_apis.exceptions.ResourceNotFoundException;
import com.blog_app.blog_app_apis.payloads.UserDto;
import com.blog_app.blog_app_apis.repositories.UserRepo;
import com.blog_app.blog_app_apis.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
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
    public UserDto createUser(UserDto user) {
        User user1=this.DtoToUser(user);
        User savedUser=this.userRepo.save(user1);
        return this.UserToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updateduser=this.userRepo.save(user);
        return this.UserToUserDto(updateduser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User  user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        return this.UserToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
       List<User> users=this.userRepo.findAll();

        return users.stream().map(user -> this.UserToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        this.userRepo.delete(user);
    }

    private User DtoToUser(UserDto userDto){
        User user=this.modelMapper.map(userDto,User.class) ;
//        User user=new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return user;
    }
    private UserDto UserToUserDto(User user){
        UserDto userDto =this.modelMapper.map(user,UserDto.class);
//        UserDto userDto= new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return userDto;
    }
}
