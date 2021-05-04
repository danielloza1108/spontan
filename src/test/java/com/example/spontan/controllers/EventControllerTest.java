package com.example.spontan.controllers;

import com.example.spontan.dto.EventDTO;
import com.example.spontan.entity.Event;
import com.example.spontan.exception.EventNotExistException;
import com.example.spontan.service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EventController.class)
class EventControllerTest {

    private static final String ENDPOINT_CREATE_EVENT = "/api/event/add";
    private static final String ENDPOINT_GET_EVENT_BY_ID = "/api/event/get/{eventId}";
    public static final String EVENT_NAME = "event name";
    public static final String EVENT_PLACE = "Warszawa";
    public static final int QUANTITY_OF_PLAYERS = 11;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventService eventService;

    @Test
    void shouldAddEvent() throws Exception {
        EventDTO requestEventDto = getEventDTO();
        String requestJson = objectMapper.writeValueAsString(requestEventDto);

        when(eventService.addEvent(any(EventDTO.class))).thenReturn(getEvent());

        mockMvc.perform(post(ENDPOINT_CREATE_EVENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(EVENT_NAME))
                .andExpect(jsonPath("$.quantity").value(QUANTITY_OF_PLAYERS));
    }


    @Test
    void shouldGetEventById() throws Exception {
        Long eventId = 1L;
        EventDTO requestEventDto = getEventDTO();
        String requestJson = objectMapper.writeValueAsString(requestEventDto);

        when(eventService.getEventById(String.valueOf(eventId))).thenReturn(requestEventDto);

        mockMvc.perform(get(ENDPOINT_GET_EVENT_BY_ID, eventId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(jsonPath("$.name").value(EVENT_NAME))
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(requestJson));

        verify(eventService,times(1)).getEventById(String.valueOf(1L));
    }

    @Test
    void shouldGetListOfUsersFromEventById() {

        EventDTO eventDto = new EventDTO();
        eventDto.setName(EVENT_NAME);
        eventDto.setQuantityOfPlayers(QUANTITY_OF_PLAYERS);
        eventDto.setEventStart(LocalDateTime.now());
        eventDto.setDurationOfTheEvent(LocalDateTime.now().plusMinutes(90));
        eventDto.setEventPlace("Warszawa");
    }


    private Event getEvent() {
        Event createdEvent = new Event();
        createdEvent.setId(1L);
        createdEvent.setName(EVENT_NAME);
        createdEvent.setQuantityOfPlayers(QUANTITY_OF_PLAYERS);
        return createdEvent;
    }

    private EventDTO getEventDTO() {
        EventDTO eventDto = new EventDTO();
        eventDto.setName(EVENT_NAME);
        eventDto.setQuantityOfPlayers(QUANTITY_OF_PLAYERS);
        eventDto.setEventStart(LocalDateTime.now());
        eventDto.setDurationOfTheEvent(LocalDateTime.now().plusMinutes(90));
        eventDto.setEventPlace("Warszawa");
        return eventDto;
    }
}
