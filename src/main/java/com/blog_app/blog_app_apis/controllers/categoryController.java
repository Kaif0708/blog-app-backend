package com.blog_app.blog_app_apis.controllers;

import com.blog_app.blog_app_apis.payloads.ApiResponse;
import com.blog_app.blog_app_apis.payloads.CategoryDto;
import com.blog_app.blog_app_apis.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class categoryController {
    @Autowired
    private CategoryService categoryService;
    //create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createCatDto=this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createCatDto, HttpStatus.CREATED);
    }
    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
        CategoryDto updateCatDto=this.categoryService.updateCategory(categoryDto,categoryId);
        return ResponseEntity.ok(updateCatDto);
    }

    //getbyid
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId){
        CategoryDto categoryDto=this.categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    //getAll
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategoryAll(){
        return ResponseEntity.ok(this.categoryService.getAllCategory());
    }
    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category deleted",true),HttpStatus.OK);
    }

}
