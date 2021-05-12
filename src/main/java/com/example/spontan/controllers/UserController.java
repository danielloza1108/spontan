package com.example.spontan.controllers;

import com.example.spontan.dto.UserDTO;
import com.example.spontan.service.UserService;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<UserDTO> getUser(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUserByEmail(email));
    }

    @GetMapping(value = "/getFriendsId/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Long>> getFriends(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getFriendsId(email));
    }

    @GetMapping(value = "/getUserById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUserById(id));
    }

    @PostMapping(value = "/addSkill")
    public ResponseEntity<String> addSkillToUser(@RequestBody String json) throws JSONException {
        userService.addSkillToUser(json);
        return ResponseEntity.ok("Success");
    }

    @GetMapping(value = "/showSkills/{email}")
    public ResponseEntity<List<Map<String, String>>> showSkills(@PathVariable String email) throws JSONException {
        return ResponseEntity.ok().body(userService.getAllUserSkills(email));
    }

    @GetMapping(value = "/showSkillsByUsers/{email}")
    public ResponseEntity<List<Map<String, String>>> showSkillsAddedByUsers(@PathVariable String email) throws JSONException {
        return ResponseEntity.ok().body(userService.getAllUserSkillsAddedByUsers(email));
    }
}
