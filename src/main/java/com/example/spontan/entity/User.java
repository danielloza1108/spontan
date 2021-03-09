package com.example.spontan.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "appUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private final String uuid = UUID.randomUUID().toString();

    @OneToMany
    private List<Event> createdEvent;

    @ManyToMany
    private List<User> friends;

    @OneToMany(mappedBy = "user")
    private List<Skill> skills;

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof User &&
                Objects.equals(uuid, ((User) o).uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    public User() {
    }

    public List<Event> getCreatedEvent() {
        return createdEvent;
    }

    public void setCreatedEvent(List<Event> createdEvent) {
        this.createdEvent = createdEvent;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
