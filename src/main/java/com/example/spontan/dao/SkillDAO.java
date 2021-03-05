package com.example.spontan.dao;

import com.example.spontan.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SkillDAO extends JpaRepository<Skill, Long> {
    @Query(value = "SELECT skills_id FROM user_skills WHERE user_id = :id",nativeQuery = true)
    List<Long> findAllSkillsByUserId(@Param("id") Long id);
    Optional<Skill> findById(Long id);
}
