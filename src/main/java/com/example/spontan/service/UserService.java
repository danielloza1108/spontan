package com.example.spontan.service;

import com.example.spontan.DAO.UserDAO;
import com.example.spontan.entity.User;
import com.example.spontan.exception.UserAlreadyInDBException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    public void saveUser(User user){
        if(userDAO.findByEmail(user.getEmail()) != null){
            throw new UserAlreadyInDBException("User with this email is already taken");
        }
        userDAO.save(user);
    }
}
