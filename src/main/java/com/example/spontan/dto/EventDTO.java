package com.example.spontan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class EventDTO {

    private String name;
    private Integer quantityOfPlayers;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Europe/Warsaw")
    private LocalDateTime eventStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Europe/Warsaw")
    private LocalDateTime durationOfTheEvent;
    private String eventPlace;
    private CategoryDTO categoryDTO;

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public String getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
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

    public LocalDateTime getDurationOfTheEvent() {
        return durationOfTheEvent;
    }

    public void setDurationOfTheEvent(LocalDateTime durationOfTheEvent) {
        this.durationOfTheEvent = durationOfTheEvent;
    }
}
