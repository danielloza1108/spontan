package com.example.spontan.controllers;

import com.example.spontan.dto.EventDTO;
import com.example.spontan.dto.UserDTO;
import com.example.spontan.entity.Event;
import com.example.spontan.exception.EventHaveNoUsersException;
import com.example.spontan.exception.UserIsNotInTheEventException;
import com.example.spontan.service.EventService;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
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
    //dodac kategorie
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> addEvent(@RequestBody EventDTO eventDTO){
        Event event = eventService.addEvent(eventDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    @GetMapping(value = "/get/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long eventId) throws JSONException {
        return ResponseEntity.status(HttpStatus.FOUND).body(eventService.getEventById(String.valueOf(eventId)));
    }

    @GetMapping(value = "/getUserList/{eventId}")
    public ResponseEntity<List<UserDTO>> getUsersJoinedToEvent(@PathVariable String eventId) throws EventHaveNoUsersException {
        List<UserDTO> usersFromEvent = eventService.getUsersFromEvent(Long.parseLong(eventId));
        return ResponseEntity.status(HttpStatus.FOUND).body(usersFromEvent);
    }

    @PostMapping(value = "/joinUserToEvent")
    public ResponseEntity<String> joinUserToEvent(@RequestBody String json) throws JSONException {
        EventDTO eventById = eventService.getEventById(json);
        eventService.joinToEventByUser(json);
        return ResponseEntity.ok("User joined to event name: " + eventById.getName());
    }

    @PostMapping(value = "/delete/user")
    public ResponseEntity<String> deleteUserFromEvent(@RequestBody String json) throws UserIsNotInTheEventException, JSONException, EventHaveNoUsersException {
        eventService.deleteUserFromEvent(json);
        return ResponseEntity.ok("deleted user from event!");
    }

    @DeleteMapping(value = "/delete/{eventId}")
    public ResponseEntity<String> deleteEventById(@PathVariable Long eventId){
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok("event deleted");
    }
}
