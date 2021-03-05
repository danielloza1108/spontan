package com.example.spontan.dao;

import com.example.spontan.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryDAO extends JpaRepository<Category, Long> {

    Category findCategoryByName(String name);
    @Query(value = "SELECT * FROM category", nativeQuery = true)
    List<Category> getAll();
}
