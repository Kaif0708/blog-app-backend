package com.blog_app.blog_app_apis.services;

import com.blog_app.blog_app_apis.entities.Category;
import com.blog_app.blog_app_apis.entities.Post;
import com.blog_app.blog_app_apis.payloads.PostDto;
import com.blog_app.blog_app_apis.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,Integer user_Id,Integer category_id);
    PostDto updatePost(PostDto postDto,Integer postId);
    void deletePost(Integer postId);

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy,String sortDir);
    PostDto getPostById(Integer postId);
    List<PostDto> getPostsByCategory(Integer category_id);
    List<PostDto> getPostsByUser(Integer user_id);

    //search posts
    List<PostDto> searchPosts(String keyword);


}
