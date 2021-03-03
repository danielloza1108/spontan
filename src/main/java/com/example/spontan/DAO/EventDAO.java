package com.example.spontan.DAO;

import com.example.spontan.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventDAO extends JpaRepository<Event,Long> {
    Event findByPlace(String place);
    List<Event> findEventsByPlace (String place);
}
