package com.example.spontan.controllers;

import com.example.spontan.dto.EventDTO;
import com.example.spontan.dto.UserDTO;
import com.example.spontan.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    private static final String ENDPOINT_ADD_USER = "/api/user/add";
    private static final String USER_NAME = "Rafal  ";
    private static final String USER_EMAIL = "rafal@email.com" ;

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
    void getUser() {
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