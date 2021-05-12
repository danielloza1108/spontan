package com.example.spontan.controllers;

import com.example.spontan.dto.SkillDTO;
import com.example.spontan.dto.UserDTO;
import com.example.spontan.entity.AppUser;
import com.example.spontan.entity.Category;
import com.example.spontan.entity.Skill;
import com.example.spontan.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    private static final String ENDPOINT_ADD_USER = "/api/user/add";
    private static final String ENDPOINT_CHANGE_PASSWORD = "/api/user/edit/password";
    private static final String ENDPOINT_GET_USER_BY_EMAIL = "/api/user/get/{email}";
    private static final String ENDPOINT_GET_USER_BY_ID = "/api/user/getUserById/{id}";
    private static final String ENDPOINT_ADD_SKILL_TO_USER = "/api/user/addSkill";
    private static final String ENDPOINT_SHOW_USER_SKILLS = "/api/user/showSkills/{email}";
    private static final String ENDPOINT_SHOW_SKILLS_BY_USER = "/api/user/showSkillsByUsers/{email}";
    private static final String ENDPOINT_ADD_FRIEND = "/api/user/addFriend";
    private static final String ENDPOINT_GET_USER_FRIENDS = "/api/user/getFriendsId/{email}";


    private static final String USER_NAME = "Rafal";
    private static final String USER_EMAIL = "rafal@email.com";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void shouldAddUser() throws Exception {

        UserDTO userDTO1 = getUserDto();
        String requestedUserDTO = objectMapper.writeValueAsString(userDTO1);

        mockMvc.perform(post(ENDPOINT_ADD_USER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestedUserDTO))
                .andExpect(status().isOk());
        verify(userService, Mockito.times(1)).saveUser(isA(UserDTO.class));
    }

    @Test
    void editPassword() throws Exception {

        UserDTO userDTO1 = getUserDto();
        String requestedUserDTO = objectMapper.writeValueAsString(userDTO1);

        mockMvc.perform(post(ENDPOINT_CHANGE_PASSWORD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestedUserDTO))
                .andExpect(status().isOk());

        verify(userService, times(1)).editPassword(isNotNull());
    }


    @Test
    void addFriend() throws Exception {

        UserDTO userDTO1 = getUserDto();
        String requestedUserDTO = objectMapper.writeValueAsString(userDTO1);

        mockMvc.perform(post(ENDPOINT_ADD_FRIEND)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestedUserDTO))
                .andExpect(status().isOk());
        verify(userService, Mockito.times(1)).addFriend(requestedUserDTO);
    }

    @Test
    void shouldReturnUserFoundByEmail() throws Exception {

        UserDTO userDTO = getUserDto();
        String requestedUserDTO = objectMapper.writeValueAsString(userDTO);

        when(userService.getUserByEmail(USER_EMAIL)).thenReturn(getUserDto());

        mockMvc.perform(get(ENDPOINT_GET_USER_BY_EMAIL, USER_EMAIL))
                .andExpect(status().isFound())
                .andExpect(content().json(requestedUserDTO))
                .andExpect(jsonPath("$.name").value(USER_NAME));

    }

//    @Test
//    void getFriends() throws Exception {
//
//        AppUser appUser = getAppUser();
//        appUser.setFriends(getFriendIdList());
//        List<Long> friendsIds = new ArrayList<>();
//        for (AppUser friend : appUser.getFriends()) {
//            friendsIds.add(friend.getId());
//        }
//        Object[] bjects = Arrays.asObjectArray(objectMapper.writeValueAsString(appUser.getFriends()));
//
//        when(userService.getFriendsId(USER_EMAIL)).thenReturn(friendsIds);
//
//        mockMvc.perform(get(ENDPOINT_GET_USER_FRIENDS, USER_EMAIL))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json(java.util.Arrays.toString(bjects)));
//    }

    @Test
    void shouldReturnUserFoundById() throws Exception {
        UserDTO userDTO = getUserDto();
        String requestedUserDTO = objectMapper.writeValueAsString(userDTO);

        when(userService.getUserById(1L)).thenReturn(getUserDto());

        mockMvc.perform(get(ENDPOINT_GET_USER_BY_ID, 1L))
                .andExpect(status().isFound())
                .andExpect(content().json(requestedUserDTO))
                .andExpect(jsonPath("$.name").value(USER_NAME));


    }

    @Test
    void addSkillToUser() throws Exception {
        String requestSkillJson = objectMapper.writeValueAsString(getSkillDto());

        mockMvc.perform(post(ENDPOINT_ADD_SKILL_TO_USER)
                .content(requestSkillJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void showSkills() throws Exception {
        String requestedSkillDto = objectMapper.writeValueAsString(getSkillDto());

        when(userService.getAllUserSkills(USER_EMAIL)).thenReturn(getUserSkills());

        mockMvc.perform(get(ENDPOINT_SHOW_USER_SKILLS, USER_EMAIL)
                .content(requestedSkillDto)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rate").value(5f))
                .andExpect(jsonPath("$[0].category").value("Pilka"));
    }

    @Test
    void showSkillsAddedByUsers() throws Exception {
        String requestedSkillDto = objectMapper.writeValueAsString(getSkillDto());

        when(userService.getAllUserSkillsAddedByUsers(USER_EMAIL)).thenReturn(getUserSkills());

        mockMvc.perform(get(ENDPOINT_SHOW_SKILLS_BY_USER, USER_EMAIL)
                .content(requestedSkillDto)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].rate").value(5f))
                .andExpect(jsonPath("$[0].category").value("Pilka"));
    }

    private static List<AppUser> getFriendIdList() {
        AppUser appUser1 = new AppUser();
        appUser1.setId(1L);

        AppUser appUser2 = new AppUser();
        appUser2.setId(2L);

        List<AppUser> appUsers = new ArrayList<>();
        appUsers.add(appUser1);
        appUsers.add(appUser2);

        return appUsers;
    }

    private static UserDTO getUserDto() {
        UserDTO userDto = new UserDTO();
        userDto.setEmail("rafal@mail.com");
        userDto.setName("Rafal");
        userDto.setPassword("password");
        return userDto;
    }

    private static SkillDTO getSkillDto() {
        SkillDTO skillDTO = new SkillDTO();
        skillDTO.setRate(5f);

        return skillDTO;
    }

    private static Skill getSkill() {
        Skill skill = new Skill();
        skill.setRate(5f);
        skill.setAddedByMyself(true);
        skill.setAppUser(getAppUser());
        return skill;
    }

    private static List<Skill> getSkillList() {
        Category category = new Category();
        category.setName("Pilka");
        category.setId(1L);

        Category category2 = new Category();
        category2.setName("Hokej");
        category2.setId(2L);

        Skill skill = new Skill();
        skill.setRate(5f);
        skill.setAddedByMyself(true);
        skill.setAppUser(getAppUser());
        skill.setCategory(category);

        Skill skill2 = new Skill();
        skill2.setRate(3f);
        skill2.setAddedByMyself(true);
        skill2.setAppUser(getAppUser());
        skill.setCategory(category2);

        List<Skill> skillList = new ArrayList<>();
        skillList.add(skill);
        skillList.add(skill2);

        return skillList;
    }

    private static List<Map<String, String>> getUserSkills() {
        List<Skill> skills = getSkillList();

        List<Map<String, String>> list = new ArrayList<>();
        for (Skill skill : skills) {
            Map<String, String> map = new HashMap<>();
            map.put("rate", String.valueOf(skill.getRate()));
            map.put("category", "Pilka");
            list.add(map);
        }
        return list;

    }

    private static AppUser getAppUser() {
        AppUser appUser = new AppUser();
        appUser.setId(1L);
        appUser.setName("Marcin");
        appUser.setPassword("password");
        appUser.setEmail("rafal@email.com");
        return appUser;
    }


}