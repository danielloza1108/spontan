package com.example.spontan.dao;

import com.example.spontan.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillDAO extends JpaRepository<Skill, Long> {
}
