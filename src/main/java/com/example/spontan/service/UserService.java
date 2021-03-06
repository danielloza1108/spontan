package com.example.spontan.service;

import com.example.spontan.dao.CategoryDAO;
import com.example.spontan.dao.UserDAO;
import com.example.spontan.dto.UserDTO;
import com.example.spontan.entity.AppUser;
import com.example.spontan.entity.Category;
import com.example.spontan.entity.Skill;
import com.example.spontan.exception.CategoryNotFoundException;
import com.example.spontan.exception.UserAlreadyInDBException;
import com.example.spontan.exception.UserIsNotInTheBaseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CategoryService categoryService;
    private final SkillService skillService;
    private final CategoryDAO categoryDAO;

    public UserService(UserDAO userDAO, ModelMapper modelMapper, PasswordEncoder passwordEncoder, CategoryService categoryService, SkillService skillService, CategoryDAO categoryDAO) {
        this.userDAO = userDAO;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.categoryService = categoryService;
        this.skillService = skillService;
        this.categoryDAO = categoryDAO;
    }

    @Transactional
    public void saveUser(UserDTO userDTO) {
        AppUser appUser = modelMapper.map(userDTO, AppUser.class);
        if (userDAO.findByEmail(appUser.getEmail()) != null) {
            throw new UserAlreadyInDBException("User with this email is already taken");
        }
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        userDAO.save(appUser);

    }

    @Transactional
    public UserDTO getUserByEmail(String email) {
        if (userDAO.findByEmail(email) == null) {
            throw new UserIsNotInTheBaseException("No user in the base" + email);
        }
        AppUser appUser = userDAO.findByEmail(email);
        return modelMapper.map(appUser, UserDTO.class);

    }

    @Transactional
    public void editPassword(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        if (userDAO.findByEmail(email) == null) {
            throw new UserIsNotInTheBaseException("No user in the base");
        }
        AppUser appUser = userDAO.findByEmail(email);
        appUser.setPassword(passwordEncoder.encode(password));
        userDAO.save(appUser);
    }

    @Transactional
    public void addFriend(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String userEmail = jsonObject.getString("userEmail");
        String friendEmail = jsonObject.getString("friendEmail");
        if (userDAO.findByEmail(userEmail) == null) {
            throw new UserIsNotInTheBaseException("No user in the base");
        } else if (userDAO.findByEmail(friendEmail) == null) {
            throw new UserIsNotInTheBaseException("No friend in the base");
        }
        //For user
        AppUser appUser = userDAO.findByEmail(userEmail);
        List<AppUser> appUsers = appUser.getFriends();
        appUsers.add(userDAO.findByEmail(friendEmail));
        appUser.setFriends(appUsers);
        userDAO.save(appUser);
        //For friend
        AppUser appUser1 = userDAO.findByEmail(friendEmail);
        List<AppUser> users1 = appUser1.getFriends();
        users1.add(userDAO.findByEmail(userEmail));
        appUser1.setFriends(users1);
        userDAO.save(appUser1);
    }

    public List<Long> getFriendsId(String email) {
        if (userDAO.findByEmail(email) == null) {
            throw new UserIsNotInTheBaseException("No user in the base");
        }
        return userDAO.findFriendsById(userDAO.findByEmail(email).getId());
    }


    public UserDTO getUserById(Long id) {
        UserDTO userDTO = modelMapper.map(userDAO.getUserById(id), UserDTO.class);
        if (userDTO == null) {
            throw new UserIsNotInTheBaseException("No user in the base");
        }
        return userDTO;
    }

    @Transactional
    public void addSkillToUser(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String category = jsonObject.getString("name");
        float rate = (float) jsonObject.getDouble("rate");
        String email = jsonObject.getString("email");
        String addUserEmail = jsonObject.getString("addUserEmail");
        if (userDAO.findByEmail(email) == null) {
            throw new UserIsNotInTheBaseException("User with this email is not in the database");
        } else if (categoryDAO.findCategoryByName(category) == null) {
            throw new CategoryNotFoundException("Category with this name is not in the database");
        }
        boolean byMyself;
        if (userDAO.findByEmail(addUserEmail).getEmail().equals(userDAO.findByEmail(email).getEmail())) {
            byMyself = true;
        } else {
            byMyself = false;
        }
        Skill skill = new Skill();
        skill.setCategory(categoryService.getCategoryByName(category));
        skill.setRate(rate);
        skill.setAppUser(userDAO.findByEmail(email));
        skill.setAddedByMyself(byMyself);
        skillService.addSkill(skill);
        AppUser appUser = userDAO.findByEmail(email);
        List<Skill> skills = appUser.getSkills();
        skills.add(skill);

        appUser.setSkills(skills);
        userDAO.save(appUser);


    }

    public List<Map<String, String>> getAllUserSkills(String email) throws JSONException {
        AppUser appUser = userDAO.findByEmail(email);
        List<Skill> skills = skillService.getAllSkillsForUser(appUser.getId());

        List<Map<String, String>> list = new ArrayList<>();
        for (Skill skill : skills) {
            Map<String, String> map = new HashMap<>();
            map.put("rate", String.valueOf(skill.getRate()));
            map.put("category", skill.getCategory().getName());
            list.add(map);
        }
        return list;
    }

    public List<Map<String, String>> getAllUserSkillsAddedByUsers(String email) throws JSONException {
        AppUser appUser = userDAO.findByEmail(email);
        List<Category> categories = categoryDAO.getAll();
        List<Map<String, String>> list = new ArrayList<>();
        for (Category category : categories) {
            if(skillService.getAllSkillsForUserAddedByUsersByCategory(appUser.getId(),category.getId()).size() != 0) {
                Map<String, String> map = new HashMap<>();
                map.put("category", category.getName());
                int counter = skillService.getCountOfSkillsAddedByUsers(appUser.getId(), category.getId());
                float sumOfRates = skillService.getSumOfRatesSkillsByCategory(appUser.getId(), category.getId());
                map.put("avgOfRates", String.valueOf(sumOfRates / counter));
                map.put("counterOfRates", String.valueOf(counter));
                list.add(map);
            }
        }
        return list;
    }
}