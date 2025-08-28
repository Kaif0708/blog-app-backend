package com.blog_app.blog_app_apis.payloads;

import com.blog_app.blog_app_apis.entities.Role;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    @NotEmpty
    @Size(min=4,message = "Username must be minimum of 4 characters")
    private String name;

    @Email(message = "Email address not valid !!")
    private String email;

    @NotEmpty
    @Size(min = 3,max=8,message = "Password length should be between 3 to 10 characters.")
//    @Pattern(regexp = )
    private String password;

    @NotNull
    private String about;

    private Set<Role> roles=new HashSet<>();

}
