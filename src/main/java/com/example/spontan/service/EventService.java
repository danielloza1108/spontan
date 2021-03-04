package com.example.spontan.service;

import com.example.spontan.dao.EventDAO;
import com.example.spontan.dto.EventDTO;
import com.example.spontan.entity.Event;
import com.example.spontan.exception.EventNotExistException;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    private final EventDAO eventDAO;
    private final ModelMapper modelMapper;


    public EventService(EventDAO eventDAO, ModelMapper modelMapper) {
        this.eventDAO = eventDAO;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void addEvent(EventDTO eventDTO){
        List<Event> eventsByPlace = eventDAO.findEventsByPlace(eventDTO.getEventPlace());
        Event event = modelMapper.map(eventDTO, Event.class);
        if(eventsByPlace != null){
            eventsByPlace = eventDAO.findEventsByPlace(eventDTO.getEventPlace());
            for (Event event1 : eventsByPlace) {
                checkEventCollides(event, event1);
            }
        }
        eventDAO.save(event);
    }

    public void deleteEvent(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String eventName = jsonObject.getString("eventName");


    }


    public void checkEventCollides(Event event, Event event1) {
        if(event1.getEventStart().compareTo(event.getEventStart()) == 0){
            throw new EventNotExistException("Another event on this place in this date");
        }else if(event1.getEventStart().compareTo(event.getEventStart()) < 0){
            LocalDateTime localDateTime = event1.getEventStart();
            localDateTime = localDateTime.plusHours(event1.getDurationOfTheEvent().getHour());
            localDateTime = localDateTime.plusMinutes(event1.getDurationOfTheEvent().getMinute());
            if(localDateTime.compareTo(event.getEventStart()) > 0){
                System.out.println(localDateTime);
                throw new EventNotExistException("The event collides with previous event");
            }

        }else if(event1.getEventStart().compareTo(event.getEventStart()) > 0){
            LocalDateTime localDateTime = event.getEventStart();
            localDateTime = localDateTime.plusHours(event.getDurationOfTheEvent().getHour());
            localDateTime = localDateTime.plusMinutes(event.getDurationOfTheEvent().getMinute());
            if(localDateTime.compareTo(event1.getEventStart()) > 0){
                System.out.println(localDateTime);
                throw new EventNotExistException("The event collides with next event");
            }
        }
    }


}
