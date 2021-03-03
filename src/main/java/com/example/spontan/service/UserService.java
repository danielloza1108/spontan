package com.example.spontan.service;

import com.example.spontan.dao.UserDAO;
import com.example.spontan.dto.UserDTO;
import com.example.spontan.entity.User;
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
    public UserDTO getUserByEmail(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String email = jsonObject.getString("email");
        if(userDAO.findByEmail(email) == null){
          throw new UserIsNotInTheBaseException("No user in the base" + email);
        }
        User user = userDAO.findByEmail(email);
        return modelMapper.map(user,UserDTO.class);

    }

    @Transactional
    public void editPassword(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        if(userDAO.findByEmail(email) == null){
            throw new UserIsNotInTheBaseException("No user in the base");
        }
        User user = userDAO.findByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userDAO.save(user);
    }

    @Transactional
    public void addFriend(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String userEmail = jsonObject.getString("userEmail");
        String friendEmail = jsonObject.getString("friendEmail");
        if(userDAO.findByEmail(userEmail) == null){
            throw new UserIsNotInTheBaseException("No user in the base");
        }else if(userDAO.findByEmail(friendEmail) == null){
            throw new UserIsNotInTheBaseException("No friend in the base");
        }
        //For user
        User user = userDAO.findByEmail(userEmail);
        List<User> users = user.getFriends();
        users.add(userDAO.findByEmail(friendEmail));
        user.setFriends(users);
        userDAO.save(user);
        //For friend
        User user1 = userDAO.findByEmail(friendEmail);
        List<User> users1 = user1.getFriends();
        users1.add(userDAO.findByEmail(userEmail));
        user1.setFriends(users1);
        userDAO.save(user1);
    }

    public List<Long> getFriendsId(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String email = jsonObject.getString("email");
        if(userDAO.findByEmail(email) == null){
            throw new UserIsNotInTheBaseException("No user in the base");
        }
        return userDAO.findFriendsById(userDAO.findByEmail(email).getId());
    }


    public UserDTO getUserById(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String userId = jsonObject.getString("userId");
        UserDTO userDTO = modelMapper.map(userDAO.getUserById(Long.parseLong(userId)), UserDTO.class);
        if(userDTO == null) {
            throw new UserIsNotInTheBaseException("No user in the base");
        }
        return userDTO;
    }
}