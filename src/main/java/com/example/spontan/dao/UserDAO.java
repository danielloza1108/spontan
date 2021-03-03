package com.example.spontan.dao;

import com.example.spontan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDAO extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query(value = "SELECT friends_id FROM user_friends WHERE user_id = :id", nativeQuery = true)
    List<Long> findFriendsById(@Param("id") Long id);

    User getUserById(Long id);

}
