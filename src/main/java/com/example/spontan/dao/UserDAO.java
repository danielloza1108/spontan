package com.example.spontan.dao;

import com.example.spontan.entity.Skill;
import com.example.spontan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDAO extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User getUserById(Long id);

    @Query(value = "SELECT friends_id FROM user_friends WHERE user_id = :id", nativeQuery = true)
    List<Long> findFriendsById(@Param("id") Long id);

    //select all users joined to event
    @Query(value = "select user_id from spontan.user inner join event_user eu on user.id = eu.user_id where event_id = :id;",nativeQuery = true)
    List<Long> userIdListWhereEventId(@Param("id") Long id);


}
