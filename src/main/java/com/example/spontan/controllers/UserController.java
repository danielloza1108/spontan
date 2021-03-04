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
    public ResponseEntity<String> addUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
        return ResponseEntity.ok("Success");
    }

    @PostMapping(value = "/edit/password")
    public ResponseEntity<String> editPassword(@RequestBody String jsonEmailAndPassword) throws JSONException {
        userService.editPassword(jsonEmailAndPassword);
        return ResponseEntity.ok("Success");
    }

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
    @GetMapping(value = "/get/{email}")
    public UserDTO getUser(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

    @GetMapping(value = "/getFriendsId/{email}", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Long> getFriends(@PathVariable String email) {
        return userService.getFriendsId(email);
    }

    @GetMapping(value = "/getUserById/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

}
