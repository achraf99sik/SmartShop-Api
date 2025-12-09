package com.smartshop.api.config;

import com.smartshop.api.enums.UserRole;
import com.smartshop.api.exception.AccessDeniedException;
import com.smartshop.api.exception.ApiErrorResponse;
import com.smartshop.api.exception.UnauthorizedException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Instant;

@Component
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        try {
            String path = request.getRequestURI();
            String method = request.getMethod();

            if (isPublic(path)) {
                chain.doFilter(req, res);
                return;
            }

            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("role") == null) {
                throw new UnauthorizedException("Login required");
            }

            UserRole role = (UserRole) session.getAttribute("role");

            if (role == UserRole.ADMIN) {
                chain.doFilter(req, res);
                return;
            }

            if (role == UserRole.CLIENT) {
                if ("GET".equals(method) && isClientReadable(path)) {
                    chain.doFilter(req, res);
                    return;
                }
                throw new AccessDeniedException("Clients cannot modify this resource");
            }

            throw new AccessDeniedException("Access denied");

        } catch (UnauthorizedException ex) {
            writeJsonResponse(response, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", ex.getMessage(), request.getRequestURI());
        } catch (AccessDeniedException ex) {
            writeJsonResponse(response, HttpStatus.FORBIDDEN, "FORBIDDEN", ex.getMessage(), request.getRequestURI());
        }
    }

    private boolean isPublic(String path) {
        return path.startsWith("/api/auth/login");
    }

    private boolean isClientReadable(String path) {
        return path.startsWith("/api/products") ||
                path.startsWith("/api/clients") ||
                path.startsWith("/api/orders") ||
                path.startsWith("/api/auth/login");
    }

    private void writeJsonResponse(HttpServletResponse response, HttpStatus status, String error, String message, String path) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        ApiErrorResponse body = new ApiErrorResponse(
                Instant.now().toString(),
                status.value(),
                error,
                message,
                path
        );
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    }
}


