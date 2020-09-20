package com.jeffdev.prueba.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



public class AuthorizationFilter extends OncePerRequestFilter {


    private String jwtKey;

    public AuthorizationFilter(String jwtKey) {
        this.jwtKey = jwtKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain
    ) throws ServletException, IOException {
        String header = httpServletRequest.getHeader("Authorization");
        if (headerIsValid(header)) {
            try {
                Claims claims = getClaims(getToken(header));
                Optional.ofNullable(claims.getSubject())
                        .ifPresent(username -> setUserContext(claims, username));
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }
        goToNextFilter(httpServletRequest, httpServletResponse, filterChain);
    }

    private boolean headerIsValid(String header) {
        return header != null && header.startsWith("Bearer ");
    }

    private String getToken(String header) {
        return header.replace("Bearer ", "");
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private void setUserContext(Claims claims, String username) {
        User userDetails = new User(username, "", Collections.emptyList());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                getGrantedAuthorities(claims)
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @SuppressWarnings("unchecked")
    private List<SimpleGrantedAuthority> getGrantedAuthorities(Claims claims) {
        return ((List<String>) claims.get("authorities")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private void goToNextFilter(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
