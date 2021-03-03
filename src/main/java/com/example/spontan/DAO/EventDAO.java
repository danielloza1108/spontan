package com.example.spontan.DAO;

import com.example.spontan.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDAO extends JpaRepository<Event,Long> {
}
