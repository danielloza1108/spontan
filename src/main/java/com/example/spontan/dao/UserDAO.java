package com.example.spontan.dao;

import com.example.spontan.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDAO extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);
    AppUser getUserById(Long id);

    @Query(value = "SELECT friends_id FROM user_friends WHERE user_id = :id", nativeQuery = true)
    List<Long> findFriendsById(@Param("id") Long id);

    //select all users joined to event
    @Query(value = "SELECT id FROM app_user INNER JOIN event_user eu ON app_user.id = eu.user_id WHERE event_id = :eventId",nativeQuery = true)
    List<Long> userIdListWhereEventId(@Param("eventId") Long id);
    @Modifying
    @Query(value = "INSERT INTO app_user_created_event(app_user_id,created_event_id) VALUES (:userId,:eventId)",nativeQuery = true)
    void saveUserToCreatedEvent(@Param("userId") Long userId, @Param("eventId") Long eventId);

}
