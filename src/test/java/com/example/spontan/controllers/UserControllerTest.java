package com.example.spontan.controllers;

import com.example.spontan.dto.EventDTO;
import com.example.spontan.dto.UserDTO;
import com.example.spontan.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    private static final String ENDPOINT_ADD_USER = "/api/user/add";
    private static final String USER_NAME = "Rafal  ";
    private static final String USER_EMAIL = "rafal@email.com";
    private static final String ENDPOINT_GET_USER_BY_EMAIL = "/api/user/get/{email}";

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
    }

    @Test
    void editPassword() {

    }

    @Test
    void addFriend() {
    }

    @Test
    void shouldReturnUserFoundByEmail() throws Exception {

        UserDTO userDTO = getUserDto();
        String requestedUserDTO = objectMapper.writeValueAsString(userDTO);

        when(userService.getUserByEmail(USER_EMAIL)).thenReturn(getUserDto());
        mockMvc.perform(get(ENDPOINT_GET_USER_BY_EMAIL, USER_EMAIL))
                .andExpect(status().isOk())
                .andExpect(content().json(requestedUserDTO))
                .andExpect(jsonPath("$.name").value(USER_NAME));

    }

    @Test
    void getFriends() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void addSkillToUser() {
    }

    @Test
    void showSkills() {
    }

    @Test
    void showSkillsAddedByUsers() {
    }


    private static UserDTO getUserDto() {
        UserDTO userDto = new UserDTO();
        userDto.setEmail("rafal@mail.com");
        userDto.setName("Rafal");
        userDto.setPassword("password");
        return userDto;
    }
}