package com.example.spontan.controllers;

import com.example.spontan.dto.EventDTO;
import com.example.spontan.service.EventService;
import org.json.JSONException;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(name = "/get/{eventId}", produces= MediaType.APPLICATION_JSON_VALUE)
    public EventDTO getEventById(@PathVariable String eventId) throws JSONException {
        return eventService.getEventById(eventId);
    }



}
