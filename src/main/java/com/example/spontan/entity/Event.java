package com.example.spontan.entity;

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
    private Integer quantityOfPlayers;
    private Date eventStart;
    private Long organizerId;

    public Event(String name, Integer quantityOfPlayers, Date eventStart, Long organizerId) {
        this.name = name;
        this.quantityOfPlayers = quantityOfPlayers;
        this.eventStart = eventStart;
        this.organizerId = organizerId;
    }

    public Event() {

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

    public void setOrganizer(Long organizer) {
        this.organizerId = organizerId;
    }
}
