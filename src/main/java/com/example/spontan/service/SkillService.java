package com.example.spontan.service;

import com.example.spontan.dao.SkillDAO;
import com.example.spontan.dto.SkillDTO;
import com.example.spontan.entity.Category;
import com.example.spontan.entity.Skill;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SkillService {
    private final ModelMapper modelMapper;
    private final SkillDAO skillDAO;
    private final Category category;

    public SkillService(ModelMapper modelMapper, SkillDAO skillDAO, Category category) {
        this.modelMapper = modelMapper;
        this.skillDAO = skillDAO;

        this.category = category;
    }

    @Transactional
    public void addSkill(SkillDTO skillDTO){
        Skill skill = modelMapper.map(skillDTO, Skill.class);
        skillDAO.save(skill);
    }

}
