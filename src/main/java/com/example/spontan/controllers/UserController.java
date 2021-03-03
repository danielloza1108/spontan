package com.example.spontan.controllers;


import com.example.spontan.DTO.UserDTO;
import com.example.spontan.entity.User;
import com.example.spontan.service.UserService;
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

    @GetMapping(value = "/get/{email}")
    public UserDTO getUser(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

    @PostMapping(value = "/edit/{email}/{password}")
    public ResponseEntity<String> editPassword(@PathVariable String email, @PathVariable String password){
        userService.editPassword(email,password);
        return ResponseEntity.ok("Success");
    }

    @PostMapping(value = "/addFriend/{userEmail}/{friendEmail}")
    public ResponseEntity<String> addFriend(@PathVariable String userEmail, @PathVariable String friendEmail){
        userService.addFriend(userEmail,friendEmail);
        return ResponseEntity.ok("Success");
    }

}
