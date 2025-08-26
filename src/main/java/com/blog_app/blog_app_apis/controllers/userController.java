package com.blog_app.blog_app_apis.controllers;

import com.blog_app.blog_app_apis.payloads.ApiResponse;
import com.blog_app.blog_app_apis.payloads.UserDto;
import com.blog_app.blog_app_apis.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class userController {
    @Autowired
    private UserService userService ;

    //POST-create user
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createuserdto=this.userService.createUser(userDto);
        return new ResponseEntity<>(createuserdto, HttpStatus.CREATED);
    }
    //PUT-update  user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable Integer userId){
        UserDto updateuserdto=this.userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updateuserdto);
    }

    //GET- getUserById
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId){
        UserDto getuserbyid=this.userService.getUserById(userId);
        return ResponseEntity.ok(getuserbyid);
    }


    //GET- getALLUser
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUser(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }
    //DELETE- delete  user
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId){
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User deleted",true),HttpStatus.OK);
    }

}
