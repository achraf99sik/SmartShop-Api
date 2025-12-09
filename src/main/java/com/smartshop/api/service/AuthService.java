package com.smartshop.api.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.smartshop.api.exception.ResourceNotFoundException;
import com.smartshop.api.exception.UnauthorizedException;
import com.smartshop.api.model.User;
import com.smartshop.api.repository.UserRepository;
import com.smartshop.api.util.PasswordUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;

    public User login(String username, String password, HttpSession session) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        BCrypt.Result result = passwordUtil.verify(password, user.getPassword());

        if (!result.verified) {
            throw new UnauthorizedException("Invalid username or password");
        }

        session.setAttribute("userId", user.getId());
        session.setAttribute("role", user.getRole());

        return user;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
}
