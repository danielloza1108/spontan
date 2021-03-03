package com.example.spontan.dao;

import com.example.spontan.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category, Long> {

    Category findCategoryByName(String name);

}
