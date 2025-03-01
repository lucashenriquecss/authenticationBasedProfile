package com.example.authenticationBasedProfile.infrastructure.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        
        if (token == null || !token.startsWith("Bearer ")) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header", request);
            return;
        }
    
        token = token.substring(7);
        if (!jwtUtil.validateToken(token)) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid Token", request);
            return;
        }

        String email = jwtUtil.extractEmail(token);
        String profileId = jwtUtil.extractProfileId(token);
        String role = jwtUtil.extractRole(token);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null,
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.setAttribute("profileId", profileId); 

       
        chain.doFilter(request, response);
        
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message, HttpServletRequest request) throws IOException {
    response.setStatus(status.value());
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    Map<String, Object> body = new HashMap<>();
    body.put("status", status.value());
    body.put("error", status.getReasonPhrase());
    body.put("message", message);
    body.put("path", request.getRequestURI());

    ObjectMapper objectMapper = new ObjectMapper();
    response.getWriter().write(objectMapper.writeValueAsString(body));
    response.getWriter().flush();
}
}
