package com.example.spontan;

import com.example.spontan.dto.CategoryDTO;
import com.example.spontan.dto.EventDTO;
import com.example.spontan.dto.UserDTO;
import com.example.spontan.entity.Category;
import com.example.spontan.entity.Event;
import com.example.spontan.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableWebSecurity
public class SpontanApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpontanApplication.class, args);
    }

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<User, UserDTO>() {
            @Override
            protected void configure() {
                map().setEmail(source.getEmail());
                map().setName(source.getName());
            }
        });
        modelMapper.addMappings(new PropertyMap<Event, EventDTO>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setDurationOfTheEvent(source.getDurationOfTheEvent());
                map().setEventStart(source.getEventStart());
                map().setQuantityOfPlayers(source.getQuantityOfPlayers());
            }
        });
        modelMapper.addMappings(new PropertyMap<Category, CategoryDTO>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
            }
        });

        return modelMapper;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
