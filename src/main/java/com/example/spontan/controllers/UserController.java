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

    @GetMapping(value = "/get")
    public UserDTO getUser(@RequestBody String json) throws JSONException {
        return userService.getUserByEmail(json);
    }

    @PostMapping(value = "/edit")
    public ResponseEntity<String> editPassword(@RequestBody String json) throws JSONException {
        userService.editPassword(json);
        return ResponseEntity.ok("Success");
    }

    @PostMapping(value = "/addFriend")
    public ResponseEntity<String> addFriend(@RequestBody String json) throws JSONException {
        userService.addFriend(json);
        return ResponseEntity.ok("Success");
    }

}
