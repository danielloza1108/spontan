package com.example.spontan.controllers;

import com.example.spontan.dto.EventDTO;
import com.example.spontan.dto.UserDTO;
import com.example.spontan.exception.EventHaveNoUsersException;
import com.example.spontan.service.EventService;
import org.json.JSONException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = "/get/{eventId}")
    public EventDTO getEventById(@PathVariable String eventId) throws JSONException {
        return eventService.getEventById(eventId);
    }

    @GetMapping(value = "/getUserList/{eventId}")
    public ResponseEntity<List<UserDTO>> getUsersJoinedToEvent(@PathVariable String eventId) throws EventHaveNoUsersException {
        List<UserDTO> usersFromEvent = eventService.getUsersFromEvent(Long.parseLong(eventId));
        return ResponseEntity.ok(usersFromEvent);
    }

}
