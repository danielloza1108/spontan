package com.example.spontan.controllers;

import com.example.spontan.dto.EventDTO;
import com.example.spontan.dto.UserDTO;
import com.example.spontan.exception.EventHaveNoUsersException;
import com.example.spontan.service.EventService;
import org.json.JSONException;
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
    //dodac kategorie
    @PostMapping("/add")
    public ResponseEntity<String> addEvent(@RequestBody String json) throws JSONException {
        eventService.addEvent(json);
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

    @PostMapping(value = "/saveUserToEvent")
    public ResponseEntity<String> joinUserToEvent(@RequestBody String json) throws JSONException {
        EventDTO eventById = eventService.getEventById(json);
        eventService.joinToEventByUser(json);
        return ResponseEntity.ok("User joined to event name: " + eventById.getName());
    }

    @PostMapping(value = "/delete/user")
    public ResponseEntity<String> deleteUserFromEvent(@RequestBody String json)
}
