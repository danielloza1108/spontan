package com.example.spontan.controllers;


import com.example.spontan.dto.UserDTO;
import com.example.spontan.entity.User;
import com.example.spontan.service.UserService;
import org.json.JSONException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok("Success");
    }
    //In get, edit and addFriend use JSON format like:
    /*
    {
        "email" : "sth"
    }
     */
    @GetMapping(value = "/get")
    public UserDTO getUser(@RequestBody String jsonEmail) throws JSONException {
        return userService.getUserByEmail(jsonEmail);
    }
    /*
    {
        "email": "sth",
        "password": "sth"
    }
     */
    @PostMapping(value = "/edit/password")
    public ResponseEntity<String> editPassword(@RequestBody String jsonEmailAndPassword) throws JSONException {
        userService.editPassword(jsonEmailAndPassword);
        return ResponseEntity.ok("Success");
    }

    /*
    {
        "userEmail": "sth",
        "friendEmail": "sth"
    }
     */
    @PostMapping(value = "/addFriend")
    public ResponseEntity<String> addFriend(@RequestBody String jsonUserEmailAndFriendEmail) throws JSONException {
        userService.addFriend(jsonUserEmailAndFriendEmail);
        return ResponseEntity.ok("Success");
    }
    /*
    {
        "email": "sth"
    }
     */
    @GetMapping(value = "/getFriendsId", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Long> getFriends(@RequestBody String jsonEmail) throws JSONException {
        return userService.getFriendsId(jsonEmail);
    }

    @GetMapping(value = "/getUserById", produces=MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getUserById(@RequestBody String userJsonId) throws JSONException {
        return userService.getUserById(userJsonId);
    }

}
