package com.example.spontan.service;

import com.example.spontan.DAO.UserDAO;
import com.example.spontan.DTO.UserDTO;
import com.example.spontan.entity.User;
import com.example.spontan.exception.UserAlreadyInDBException;
import com.example.spontan.exception.UserIsNotInTheBase;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDAO userDAO, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void saveUser(User user) {
        if (userDAO.findByEmail(user.getEmail()) != null) {
            throw new UserAlreadyInDBException("User with this email is already taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.save(user);

    }
    @Transactional
    public UserDTO getUserByEmail(String email) {
        if(userDAO.findByEmail(email) == null){
          throw new UserIsNotInTheBase("No user in the base");
        }
        User user = userDAO.findByEmail(email);
        UserDTO userDTO = modelMapper.map(user,UserDTO.class);
        return userDTO;

    }

    @Transactional
    public void editPassword(String email,String password) {
        if(userDAO.findByEmail(email) == null){
            throw new UserIsNotInTheBase("No user in the base");
        }
        User user = userDAO.findByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userDAO.save(user);
    }

}