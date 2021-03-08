package com.example.spontan.dao;

import com.example.spontan.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventDAO extends JpaRepository<Event,Long> {
    Event findByPlace(String place);
    List<Event> findEventsByPlace (String place);
    Optional<Event> findById(Long id);
    Event findByName(String name);

}
