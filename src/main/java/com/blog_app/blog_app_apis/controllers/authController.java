package com.blog_app.blog_app_apis.controllers;

import com.blog_app.blog_app_apis.exceptions.ApiException;
import com.blog_app.blog_app_apis.payloads.JwtAuthRequest;
import com.blog_app.blog_app_apis.payloads.JwtAuthResponse;
import com.blog_app.blog_app_apis.payloads.UserDto;
import com.blog_app.blog_app_apis.security.JwtTokenHelper;
import com.blog_app.blog_app_apis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class authController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception  {
        this.authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails=this.userDetailsService.loadUserByUsername(request.getUsername());
        String token=this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse response=new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            try{
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }
        catch (BadCredentialsException e) {
            System.out.println("Invalid details.");
            throw new ApiException("Invalid username or password");
        }
    }

    //register new user api
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        UserDto userDto1=this.userService.registerUser(userDto);
        return new ResponseEntity<>(userDto1,HttpStatus.CREATED);
    }

}
