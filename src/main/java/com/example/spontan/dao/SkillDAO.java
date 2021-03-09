package com.example.spontan.dao;

import com.example.spontan.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SkillDAO extends JpaRepository<Skill, Long> {
    @Query(value = "select * from skill where user_id = :id and added_by_myself = true", nativeQuery = true)
    List<Skill> findAllSkillsByUserId(@Param("id") Long id);

    @Query(value = "select * from skill where user_id = :id and added_by_myself = false", nativeQuery = true)
    List<Skill> findAllSkillsByUserIdAddedByUsers(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) from skill where user_id = :id and added_by_myself = false and category_id = :categoryId", nativeQuery = true)
    int countAllSkillsByUserIdAddedByUsers(@Param("id") Long id, @Param("categoryId") Long categoryId);

    @Query(value = "SELECT SUM(rate) from skill where user_id = :id and added_by_myself = false and category_id= :categoryId", nativeQuery = true)
    float sumOfRatesSkillsByCategory(@Param("id") Long id, @Param("categoryId") Long categoryId);

    @Query(value = "select * from skill where user_id = :id and added_by_myself = false and category_id= :categoryId", nativeQuery = true)
    List<Skill> findAllSkillsByUserIdAddedByUsersByCategory(@Param("id") Long id, @Param("categoryId") Long categoryId);

    Optional<Skill> findById(Long id);
}
