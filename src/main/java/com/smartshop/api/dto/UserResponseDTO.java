package com.smartshop.api.dto;

import com.smartshop.api.enums.UserRole;
import lombok.Data;

import java.util.UUID;

@Data
public class UserResponseDTO {
    private UUID id;
    private String username;
    private UserRole role;
}