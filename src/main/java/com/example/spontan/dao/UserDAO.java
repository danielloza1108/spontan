package com.example.spontan.dao;

import com.example.spontan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface UserDAO extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User getUserById(Long id);

    @Query(value = "SELECT friends_id FROM user_friends WHERE user_id = :id", nativeQuery = true)
    List<Long> findFriendsById(@Param("id") Long id);

    //select all users joined to event
    @Query(value = "select id from app_user inner join event_user eu on app_user.id = eu.user_id where event_id = :eventId",nativeQuery = true)
    List<Long> userIdListWhereEventId(@Param("eventId") Long id);
    @Modifying
    @Query(value = "insert into app_user_created_event(app_user_id,created_event_id) values (:userId,:eventId)",nativeQuery = true)
    void saveUserToCreatedEvent(@Param("userId") Long userId, @Param("eventId") Long eventId);

}
