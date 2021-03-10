package com.example.spontan.config;

import org.springframework.stereotype.Component;

@Component
public class LoginCredentials {


    private String username;
    private String password;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
