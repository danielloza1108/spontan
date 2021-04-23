package com.example.spontan.controllers;

import com.example.spontan.dto.EventDTO;
import com.example.spontan.entity.Event;
import com.example.spontan.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EventController.class)
class EventControllerTest {

    private static final String ENDPOINT = "/api/event/add";
    public static final String EVENT_NAME = "event name";
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

        mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(EVENT_NAME))
                .andExpect(jsonPath("$.quantity").value(QUANTITY_OF_PLAYERS));
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
