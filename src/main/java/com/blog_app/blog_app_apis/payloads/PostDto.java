package com.blog_app.blog_app_apis.payloads;

import com.blog_app.blog_app_apis.entities.Category;
import com.blog_app.blog_app_apis.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
    private String imgName;
    private Date addDate;
    private CategoryDto category;
    private UserDto user;

}
