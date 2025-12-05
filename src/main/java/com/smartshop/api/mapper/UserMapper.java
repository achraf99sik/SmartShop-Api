package com.smartshop.api.mapper;

import com.smartshop.api.dto.UserResponseDTO;
import com.smartshop.api.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toDTO(User user);
}
