package com.example.spontan.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @JsonProperty(value = "quantity")
    private Integer quantityOfPlayers;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date eventStart;
    private Long organizerId;
    private String place;

    public Event(String name, Integer quantityOfPlayers, Date eventStart, Long organizerId, String place) {
        this.name = name;
        this.quantityOfPlayers = quantityOfPlayers;
        this.eventStart = eventStart;
        this.organizerId = organizerId;
        this.place = place;
    }

    public Event() {

    }

    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public Integer getQuantityOfPlayers() {
        return quantityOfPlayers;
    }

    public void setQuantityOfPlayers(Integer quantityOfPlayers) {
        this.quantityOfPlayers = quantityOfPlayers;
    }

    public Date getEventStart() {
        return eventStart;
    }

    public void setEventStart(Date eventStart) {
        this.eventStart = eventStart;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

}
