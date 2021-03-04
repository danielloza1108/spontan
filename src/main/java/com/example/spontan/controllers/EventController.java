package com.example.spontan.controllers;

import com.example.spontan.dto.EventDTO;
import com.example.spontan.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addEvent(@RequestBody EventDTO eventDTO){
        eventService.addEvent(eventDTO);
        return ResponseEntity.ok("Success");
    }


}
