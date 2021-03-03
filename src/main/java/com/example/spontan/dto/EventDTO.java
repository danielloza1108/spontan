package com.example.spontan.dto;

import java.time.LocalDateTime;

public class EventDTO {

    private String name;
    private Integer quantityOfPlayers;
    private LocalDateTime eventStart;
    private LocalDateTime durationOfTheEvent;



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

    public LocalDateTime getDurationOfTheEvent() {
        return durationOfTheEvent;
    }

    public void setDurationOfTheEvent(LocalDateTime durationOfTheEvent) {
        this.durationOfTheEvent = durationOfTheEvent;
    }
}
