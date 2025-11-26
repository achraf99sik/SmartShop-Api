package com.smartshop.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientRequestDTO {
    @NotBlank
    private String nom;
    @Email
    @NotBlank
    private String email;
}
