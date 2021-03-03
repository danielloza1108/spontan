package com.example.spontan.service;

import com.example.spontan.dao.EventDAO;
import com.example.spontan.entity.Event;
import com.example.spontan.exception.EventNotExistException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    private final EventDAO eventDAO;


    public EventService(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Transactional
    public void addEvent(Event event){
        if(eventDAO.findEventsByPlace(event.getPlace()) != null){
            List<Event> events = eventDAO.findEventsByPlace(event.getPlace());
            for (Event event1 : events) {
                checkEventCollides(event, event1);
            }
        }
        eventDAO.save(event);
    }

    public void deleteEvent(Event event){

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
