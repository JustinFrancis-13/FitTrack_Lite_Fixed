package com.fittrack.lite.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Reads the logged-in user id from the HTTP session and marks the request as authenticated.
 * NOTE: deliberately NOT a @Component — it is created inside SecurityConfig so it only runs
 * inside the Spring Security chain (as a @Component Spring Boot would also register it as a
 * plain servlet filter, and the security chain would then skip it and reject every request).
 */
public class SessionAuthFilter extends OncePerRequestFilter {
    public static final String USER_ID = "FITTRACK_USER_ID";

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Object id = session == null ? null : session.getAttribute(USER_ID);
        if (id != null) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(id.toString(), null,
                            List.of(new SimpleGrantedAuthority("ROLE_USER"))));
        }
        chain.doFilter(req, res);
    }
}
