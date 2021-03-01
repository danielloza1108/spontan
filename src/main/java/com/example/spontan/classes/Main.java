package com.example.spontan.classes;

import com.example.spontan.DAO.UserDAO;
import com.example.spontan.DTO.UserDTO;
import com.example.spontan.entity.User;
import com.example.spontan.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Main implements UserMapper{
    private UserDAO userDAO;

    public Main(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping("/add")
    @ResponseBody
    public String addUser(){

        User user = new User("Daniel", "danielloza@gmail.com", "qwerty");
        userDAO.save(user);
        User user1 = userDAO.findByEmail(user.getEmail());

        return UserToDto(user1).getName() + " " + UserToDto(user1).getEmail();
    }

    @Override
    public UserDTO UserToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
