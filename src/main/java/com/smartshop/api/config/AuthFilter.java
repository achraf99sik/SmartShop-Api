package com.smartshop.api.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();
        String method = request.getMethod();

        if (isPublic(path)) {
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: login required");
            return;
        }

        String role = (String) session.getAttribute("userRole");

        if (role.equals("ADMIN")) {
            chain.doFilter(req, res);
            return;
        }

        if (role.equals("CLIENT")) {

            if ("GET".equals(method) && (
                    path.startsWith("/api/products") || path.startsWith("/api/clients") || path.startsWith("/api/orders") || path.startsWith("/api/auth/login")
            )) {
                chain.doFilter(req, res);
                return;
            }

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Forbidden: Clients cannot modify resources");
            return;
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    private boolean isPublic(String path) {
        return path.startsWith("/api/auth/login");
    }
}
