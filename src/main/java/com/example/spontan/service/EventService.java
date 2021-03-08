package com.example.spontan.service;

import com.example.spontan.dao.EventDAO;
import com.example.spontan.dao.UserDAO;
import com.example.spontan.dto.EventDTO;
import com.example.spontan.dto.UserDTO;
import com.example.spontan.entity.Event;
import com.example.spontan.exception.EventHaveNoUsersException;
import com.example.spontan.exception.EventNotExistException;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private final EventDAO eventDAO;
    private final ModelMapper modelMapper;
    private final UserDAO  userDAO;


    public EventService(EventDAO eventDAO, ModelMapper modelMapper, UserDAO userDAO) {
        this.eventDAO = eventDAO;
        this.modelMapper = modelMapper;
        this.userDAO = userDAO;
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

    public EventDTO getEventById(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String eventId = jsonObject.getString("eventId");
        EventDTO eventDTO = modelMapper.map(eventDAO.findById(Long.parseLong(eventId)),EventDTO.class);
        if(eventDTO == null){
            throw new EventNotExistException("The event is not exist.");
        }
        return eventDTO;
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

    public List<UserDTO> getUsersFromEvent(Long eventId) throws EventHaveNoUsersException {
        List<Long> userIds = userDAO.userIdListWhereEventId(eventId);
        List<UserDTO> userDTOS = new ArrayList<>();
        if(userIds != null) {
            for (Long userId : userIds) {
                userDTOS.add(modelMapper.map(userDAO.getUserById(userId), UserDTO.class));
            }
        }else {
            throw new EventHaveNoUsersException("Event have no joined users");
        }

        return userDTOS;
    }
}
