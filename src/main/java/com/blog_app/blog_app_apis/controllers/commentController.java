package com.blog_app.blog_app_apis.controllers;

import com.blog_app.blog_app_apis.payloads.ApiResponse;
import com.blog_app.blog_app_apis.payloads.CommentDto;
import com.blog_app.blog_app_apis.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class commentController {

    @Autowired
    private CommentService commentService;
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
        CommentDto commentDto1=this.commentService.createComment(commentDto,postId);
        return new ResponseEntity<CommentDto>(commentDto1, HttpStatus.CREATED);
    }
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted",true),HttpStatus.OK);
    }
}
