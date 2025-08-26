package com.blog_app.blog_app_apis.services.impl;

import com.blog_app.blog_app_apis.entities.Category;
import com.blog_app.blog_app_apis.entities.Post;
import com.blog_app.blog_app_apis.entities.User;
import com.blog_app.blog_app_apis.exceptions.ResourceNotFoundException;
import com.blog_app.blog_app_apis.payloads.PostDto;
import com.blog_app.blog_app_apis.payloads.PostResponse;
import com.blog_app.blog_app_apis.repositories.CategoryRepo;
import com.blog_app.blog_app_apis.repositories.PostRepo;
import com.blog_app.blog_app_apis.repositories.UserRepo;
import com.blog_app.blog_app_apis.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer user_Id, Integer category_id) {

        User user=this.userRepo.findById(user_Id).orElseThrow(()-> new ResourceNotFoundException("User","Id",user_Id));
        Category category=this.categoryRepo.findById(category_id).orElseThrow(()-> new ResourceNotFoundException("Category","category_id",category_id));

        Post post=this.modelMapper.map(postDto,Post.class);
        post.setImgName("default.png");
        post.setAddDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost=this.postRepo.save(post);
        return this.modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImgName(postDto.getImgName());

        Post updatedPost=this.postRepo.save(post);
        return this.modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String SortBy,String sortDir) {

        Sort sort=null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(SortBy).ascending();
        }else{
            sort=Sort.by(SortBy).descending();
        }
        Pageable p=PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> postPage=this.postRepo.findAll(p);
        List<Post> posts=postPage.getContent();
        List<PostDto> postDtoList=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
       PostResponse postResponse=new PostResponse();
       postResponse.setContent(postDtoList);
       postResponse.setPageNumber(postPage.getNumber());
       postResponse.setPageSize(postPage.getSize());
       postResponse.setTotalElements(postPage.getTotalElements());
       postResponse.setTotalPage(postPage.getTotalPages());
       postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }


    @Override
    public PostDto getPostById(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post id",postId));
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer category_id) {
        Category category=this.categoryRepo.findById(category_id).orElseThrow(()-> new ResourceNotFoundException("Category","category_id",category_id));
        List<Post> posts=this.postRepo.findByCategory(category);
        List<PostDto> postDtoList=posts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtoList;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer user_id) {
        User user=this.userRepo.findById(user_id).orElseThrow(()->new ResourceNotFoundException("User","id",user_id));
        List<Post> posts=this.postRepo.findByUser(user);
        List<PostDto> postDtoList=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtoList;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        return List.of();
    }
}
