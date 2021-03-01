package com.example.spontan.DAO;

import com.example.spontan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
