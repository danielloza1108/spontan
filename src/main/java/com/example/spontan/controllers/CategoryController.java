package com.example.spontan.controllers;

import com.example.spontan.dto.CategoryDTO;
import com.example.spontan.dto.EventDTO;
import com.example.spontan.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getAll")
    public List<CategoryDTO> getAllCategories(){
       return categoryService.getAllCategories();
    }
}
