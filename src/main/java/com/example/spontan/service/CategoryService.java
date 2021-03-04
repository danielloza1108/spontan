package com.example.spontan.service;

import com.example.spontan.dao.CategoryDAO;
import com.example.spontan.dto.CategoryDTO;
import com.example.spontan.entity.Category;
import com.example.spontan.exception.CategoryExistException;
import com.example.spontan.exception.CategoryNotFoundException;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CategoryService {

    private final CategoryDAO categoryDAO;
    private final ModelMapper modelMapper;

    public CategoryService(CategoryDAO categoryDAO, ModelMapper modelMapper) {
        this.categoryDAO = categoryDAO;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void addCategory(CategoryDTO categoryDTO){
        if(categoryDAO.findCategoryByName(categoryDTO.getName()) != null) {
            throw new CategoryExistException("Category is already in base");
        }
        categoryDAO.save(modelMapper.map(categoryDTO, Category.class));
    }

    @Transactional
    public void deleteCategory(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String categoryName = jsonObject.getString("name");
        Category category = categoryDAO.findCategoryByName(categoryName);
        if(category == null){
            throw new CategoryNotFoundException("Category with this name not found");
        }
        categoryDAO.delete(category);
    }

    @Transactional
    public void updateCategory(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String categoryName = jsonObject.getString("name");
        Category category = categoryDAO.findCategoryByName(categoryName);
        if(category == null){
            throw new CategoryNotFoundException("Category not exist");
        }
        category.setName(categoryName);
        categoryDAO.save(category);
    }
}
