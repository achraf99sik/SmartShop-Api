package com.smartshop.api.service;

import com.smartshop.api.model.User;
import com.smartshop.api.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public User login(String username, String password, HttpSession session) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid username or password");
        }

        session.setAttribute("userId", user.getId());
        session.setAttribute("role", user.getRole());

        return user;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
}
