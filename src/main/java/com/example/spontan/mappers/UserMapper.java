package com.example.spontan.mappers;

import com.example.spontan.DTO.UserDTO;
import com.example.spontan.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDTO UserToDto(User user);
}
