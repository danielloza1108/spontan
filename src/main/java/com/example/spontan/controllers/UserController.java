package com.example.spontan.controllers;


import com.example.spontan.DTO.UserDTO;
import com.example.spontan.entity.User;
import com.example.spontan.service.UserService;
import com.fasterxml.jackson.databind.node.TextNode;
import org.json.JSONException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
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
    @PostMapping(value = "/edit")
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

}
