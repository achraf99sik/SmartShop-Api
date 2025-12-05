package com.smartshop.api.controller;

import com.smartshop.api.dto.LoginRequestDTO;
import com.smartshop.api.enums.UserRole;
import com.smartshop.api.mapper.ClientMapper;
import com.smartshop.api.mapper.UserMapper;
import com.smartshop.api.model.User;
import com.smartshop.api.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final ClientMapper clientMapper;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public Object login(@RequestBody LoginRequestDTO request, HttpSession session) {

        User user = authService.login(request.getUsername(), request.getPassword(), session);

        if (user.getRole() == UserRole.ADMIN) {
            return userMapper.toDTO(user);
        }

        return clientMapper.toDTO(user.getClient());
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        authService.logout(session);
    }
}
