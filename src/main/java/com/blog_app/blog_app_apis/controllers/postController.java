package com.blog_app.blog_app_apis.controllers;

import com.blog_app.blog_app_apis.config.AppConstants;
import com.blog_app.blog_app_apis.payloads.ApiResponse;
import com.blog_app.blog_app_apis.payloads.PostDto;
import com.blog_app.blog_app_apis.payloads.PostResponse;
import com.blog_app.blog_app_apis.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class postController {
    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
        PostDto postDto1=this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<PostDto>(postDto1, HttpStatus.CREATED);
    }
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
        List<PostDto> postDtoList=this.postService.getPostsByUser(userId);
        return ResponseEntity.ok(postDtoList);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
        List<PostDto> postDtoList=this.postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDtoList);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                    @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                    @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                    @RequestParam(value="sortDir",defaultValue = AppConstants.SORT_ORDER,required = false) String sortDir){
        PostResponse allPosts=this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(allPosts);
    }
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto=this.postService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto postDto1=this.postService.updatePost(postDto,postId);
        return ResponseEntity.ok(postDto1);
    }
    @DeleteMapping("posts/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ApiResponse("Post deleted",true);
    }
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword){
        List<PostDto> postDto=this.postService.searchPosts(keyword);
        return ResponseEntity.ok(postDto);

    }



}
