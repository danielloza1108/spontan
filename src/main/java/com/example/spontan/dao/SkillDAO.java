package com.example.spontan.dao;

import com.example.spontan.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillDAO extends JpaRepository<Skill, Long> {
}
