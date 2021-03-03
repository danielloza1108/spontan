package com.example.spontan.service;

import com.example.spontan.DAO.EventDAO;
import com.example.spontan.entity.Event;
import com.example.spontan.exception.EventNotExist;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EventService {
    private final EventDAO eventDAO;


    public EventService(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Transactional
    public void addEvent(Event event){
        if(eventDAO.findByPlace(event.getPlace()) != null){
            List<Event> events = eventDAO.findEventsByPlace(event.getPlace());
            for (Event event1 : events) {
                if(event1.getEventStart().compareTo(event.getEventStart()) == 0){
                    throw new EventNotExist("Another event on this place in this date");
                }
            }

        }
        eventDAO.save(event);
    }
}
