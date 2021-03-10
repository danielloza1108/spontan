package com.example.spontan.service;

import com.example.spontan.dao.CategoryDAO;
import com.example.spontan.dao.EventDAO;
import com.example.spontan.dao.UserDAO;
import com.example.spontan.dto.CategoryDTO;
import com.example.spontan.dto.EventDTO;
import com.example.spontan.dto.UserDTO;
import com.example.spontan.entity.AppUser;
import com.example.spontan.entity.Category;
import com.example.spontan.entity.Event;
import com.example.spontan.exception.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventDAO eventDAO;
    private final ModelMapper modelMapper;
    private final UserDAO userDAO;
    private final CategoryDAO categoryDAO;


    public EventService(EventDAO eventDAO, ModelMapper modelMapper, UserDAO userDAO, CategoryDAO categoryDAO) {
        this.eventDAO = eventDAO;
        this.modelMapper = modelMapper;
        this.userDAO = userDAO;
        this.categoryDAO = categoryDAO;
    }

    @Transactional
    public void addEvent(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName(jsonObject.getString("name"));
        eventDTO.setQuantityOfPlayers(jsonObject.getInt("quantityOfPlayers"));
        eventDTO.setDurationOfTheEvent(parseStringToLocalDateTime(jsonObject.getString("durationOfTheEvent")));
        eventDTO.setEventStart(parseStringToLocalDateTime(jsonObject.getString("eventStart")));
        eventDTO.setEventPlace(jsonObject.getString("eventPlace"));
        Category category = categoryDAO.findCategoryByName(jsonObject.getString("category"));
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        Long userId = jsonObject.getLong("userId");
        List<Event> eventsByPlace = eventDAO.findEventsByPlace(eventDTO.getEventPlace());
        Event event = modelMapper.map(eventDTO, Event.class);
        if (eventsByPlace != null) {
            eventsByPlace = eventDAO.findEventsByPlace(eventDTO.getEventPlace());
            for (Event event1 : eventsByPlace) {
                checkEventCollides(event, event1);
            }
        }
        if (categoryDAO.findCategoryByName(categoryDTO.getName()) == null) {
            throw new CategoryNotFoundException("This category isn't exist");
        }
        if (userDAO.getUserById(userId) == null) {
            throw new UserIsNotInTheBaseException("User is not in the base");
        }
        event.setCategory(category);
        eventDAO.save(event);
        userDAO.saveUserToCreatedEvent(userId, event.getId());

    }

    public void deleteEvent(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String eventName = jsonObject.getString("eventName");

    }

    public EventDTO getEventById(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String eventId = jsonObject.getString("eventId");
        EventDTO eventDTO = modelMapper.map(eventDAO.findById(Long.parseLong(eventId)), EventDTO.class);
        if (eventDTO == null) {
            throw new EventNotExistException("The event is not exist.");
        }
        return eventDTO;
    }


    public void checkEventCollides(Event event, Event event1) {
        if (event1.getEventStart().compareTo(event.getEventStart()) == 0) {
            throw new EventNotExistException("Another event on this place in this date");
        } else if (event1.getEventStart().compareTo(event.getEventStart()) < 0) {
            LocalDateTime localDateTime = event1.getEventStart();
            localDateTime = localDateTime.plusHours(event1.getDurationOfTheEvent().getHour());
            localDateTime = localDateTime.plusMinutes(event1.getDurationOfTheEvent().getMinute());
            if (localDateTime.compareTo(event.getEventStart()) > 0) {
                System.out.println(localDateTime);
                throw new EventNotExistException("The event collides with previous event");
            }

        } else if (event1.getEventStart().compareTo(event.getEventStart()) > 0) {
            LocalDateTime localDateTime = event.getEventStart();
            localDateTime = localDateTime.plusHours(event.getDurationOfTheEvent().getHour());
            localDateTime = localDateTime.plusMinutes(event.getDurationOfTheEvent().getMinute());
            if (localDateTime.compareTo(event1.getEventStart()) > 0) {
                System.out.println(localDateTime);
                throw new EventNotExistException("The event collides with next event");
            }
        }
    }

    public List<UserDTO> getUsersFromEvent(Long eventId) throws EventHaveNoUsersException {
        List<Long> userIds = userDAO.userIdListWhereEventId(eventId);
        List<UserDTO> userDTOS = new ArrayList<>();
        if (userIds != null) {
            for (Long userId : userIds) {
                userDTOS.add(modelMapper.map(userDAO.getUserById(userId), UserDTO.class));
            }
        } else {
            throw new EventHaveNoUsersException("Event have no joined users");
        }

        return userDTOS;
    }

    public LocalDateTime parseStringToLocalDateTime(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(string, formatter);
        return dateTime;
    }

    @Transactional
    public void joinToEventByUser(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        Long eventId = jsonObject.getLong("eventId");
        Long userId = jsonObject.getLong("userId");
        Optional<Event> eventById = eventDAO.findById(eventId);
        if (eventById.isEmpty()) {
            throw new EventNotExistException("Event is not exist");
        }
        Optional<AppUser> userById = userDAO.findById(userId);
        List<AppUser> list = eventById.get().getAppUser();
        list.add(userById.get());

        eventById.get().setAppUser(list);
        eventDAO.save(eventById.get());
    }

    public void deleteUserFromEvent(String json) throws JSONException, EventHaveNoUsersException, UserIsNotInTheEventException {
        JSONObject jsonObject = new JSONObject(json);
        Long eventId = jsonObject.getLong("eventId");
        Long userId = jsonObject.getLong("userId");
        Optional<Event> eventById = eventDAO.findById(eventId);
        if (eventById.isEmpty()) {
            throw new EventNotExistException("Event is not exist");
        }
        Optional<AppUser> userById = userDAO.findById(userId);
        if (userById.isEmpty()) {
            throw new UserIsNotInTheBaseException("No user to delete");
        }
        List<AppUser> list = eventById.get().getAppUser();
        if (!isUserInEvent(userById.get().getId(), eventById.get())) {
          throw new UserIsNotInTheEventException("User is not in event");
        }
        list.remove(userById.get());
        eventDAO.save(eventById.get());
    }

    public boolean isUserInEvent(Long userId, Event event) throws EventHaveNoUsersException {
        List<UserDTO> userList = getUsersFromEvent(event.getId());
        Optional<AppUser> userById = userDAO.findById(userId);
        for (UserDTO userDTO : userList) {
            if (userDTO.getEmail().equals(userById.get().getEmail())) {
                return true;
            }
        }
        return false;
    }


}
