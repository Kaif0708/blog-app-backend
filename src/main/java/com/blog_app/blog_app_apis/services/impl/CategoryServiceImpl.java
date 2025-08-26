package com.blog_app.blog_app_apis.services.impl;

import com.blog_app.blog_app_apis.entities.Category;
import com.blog_app.blog_app_apis.exceptions.ResourceNotFoundException;
import com.blog_app.blog_app_apis.payloads.CategoryDto;
import com.blog_app.blog_app_apis.repositories.CategoryRepo;
import com.blog_app.blog_app_apis.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category= this.DtoToCategory(categoryDto);
        return this.CategoryToDto(this.categoryRepo.save(category));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        return this.CategoryToDto(this.categoryRepo.save(category));
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","CategooryId",categoryId));
        return this.CategoryToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories=this.categoryRepo.findAll();
        return categories.stream().map(category -> this.CategoryToDto(category)).collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId",categoryId));
        this.categoryRepo.delete(category);
    }

    private CategoryDto CategoryToDto(Category category){
        return this.modelMapper.map(category,CategoryDto.class);
    }
    private Category DtoToCategory(CategoryDto categoryDto){
        return this.modelMapper.map(categoryDto,Category.class);
    }
}
