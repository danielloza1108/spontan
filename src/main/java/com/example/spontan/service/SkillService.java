package com.example.spontan.service;

import com.example.spontan.dao.SkillDAO;
import com.example.spontan.entity.Skill;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class SkillService {
    private final ModelMapper modelMapper;
    private final SkillDAO skillDAO;

    public SkillService(ModelMapper modelMapper, SkillDAO skillDAO) {
        this.modelMapper = modelMapper;
        this.skillDAO = skillDAO;
    }

    @Transactional
    public void addSkill(Skill skill) {
        skillDAO.save(skill);
    }

    public List<Skill> getAllSkillsForUser(Long userId) {
        return skillDAO.findAllSkillsByUserId(userId);
    }

    public List<Skill> getAllSkillsForUserAddedByUsers(Long userId) {
        return skillDAO.findAllSkillsByUserIdAddedByUsers(userId);
    }

    public List<Skill> getAllSkillsForUserAddedByUsersByCategory(Long userId, Long categoryId) {
        return skillDAO.findAllSkillsByUserIdAddedByUsersByCategory(userId, categoryId);
    }

    public int getCountOfSkillsAddedByUsers(Long userId, Long categoryId) {
        return skillDAO.countAllSkillsByUserIdAddedByUsers(userId, categoryId);
    }

    public float getSumOfRatesSkillsByCategory(Long userId, Long categoryId) {
        return skillDAO.sumOfRatesSkillsByCategory(userId, categoryId);
    }


    public Optional<Skill> findById(Long id) {
        return skillDAO.findById(id);
    }

}
