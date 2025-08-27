package com.blog_app.blog_app_apis.services.impl;

import com.blog_app.blog_app_apis.entities.Comment;
import com.blog_app.blog_app_apis.entities.Post;
import com.blog_app.blog_app_apis.exceptions.ResourceNotFoundException;
import com.blog_app.blog_app_apis.payloads.CommentDto;
import com.blog_app.blog_app_apis.repositories.CommentRepo;
import com.blog_app.blog_app_apis.repositories.PostRepo;
import com.blog_app.blog_app_apis.repositories.UserRepo;
import com.blog_app.blog_app_apis.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
       Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
       Comment comment= this.modelMapper.map(commentDto, Comment.class);
       comment.setPost(post);
        return this.modelMapper.map(this.commentRepo.save(comment),CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));
        this.commentRepo.delete(comment);
    }
}
