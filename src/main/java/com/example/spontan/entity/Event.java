package com.example.spontan.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final String uuid = UUID.randomUUID().toString();

    private String name;
    @JsonProperty(value = "quantity")
    private Integer quantityOfPlayers;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Europe/Warsaw")
    private LocalDateTime eventStart;
    private Long organizerId;
    private String place;
    @JsonProperty(value = "duration")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Europe/Warsaw")
    private LocalDateTime durationOfTheEvent;

    public Event(String name, Integer quantityOfPlayers, LocalDateTime eventStart, Long organizerId, String place, LocalDateTime durationOfTheEvent) {
        this.name = name;
        this.quantityOfPlayers = quantityOfPlayers;
        this.eventStart = eventStart;
        this.organizerId = organizerId;
        this.place = place;
        this.durationOfTheEvent = durationOfTheEvent;
    }

    public Event() {

    }

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof Event &&
                Objects.equals(uuid, ((Event) o).uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    public LocalDateTime getDurationOfTheEvent() {
        return durationOfTheEvent;
    }

    public void setDurationOfTheEvent(LocalDateTime durationOfTheEvent) {
        this.durationOfTheEvent = durationOfTheEvent;
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

    public LocalDateTime getEventStart() {
        return eventStart;
    }

    public void setEventStart(LocalDateTime eventStart) {
        this.eventStart = eventStart;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

}
