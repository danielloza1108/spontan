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
    public void addSkill(Skill skill){
        skillDAO.save(skill);
    }

    public List<Skill> getAllIdsForUser(Long userId){
        return skillDAO.findAllSkillsByUserId(userId);
    }

    public Optional<Skill> findById(Long id){
        return skillDAO.findById(id);
    }

}
