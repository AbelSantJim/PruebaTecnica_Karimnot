package com.example.PruebaTecnica.security;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain
    ) throws ServletException, IOException {

        String path = req.getServletPath();
        String method = req.getMethod();

        // ✅ SALTAR LOGIN Y PREFLIGHT CORS
        if (path.equals("/api/pruebaTecnica/login") || method.equals("OPTIONS")) {
            chain.doFilter(req, res);
            return;
        }

        String header = req.getHeader("Authorization");

        System.out.println("🔍 Header: " + header);

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            System.out.println("🔍 Token: " + token);

            if (jwtUtil.validateToken(token)) {

                String email = jwtUtil.extractEmail(token);
                String role = jwtUtil.extractRole(token);

                List<SimpleGrantedAuthority> authorities =
                        role != null
                                ? Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
                                : Collections.emptyList();

                var auth = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        authorities
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                System.out.println("❌ Token inválido");
            }

        } else {
            System.out.println("❌ No hay token o formato incorrecto");
        }

        chain.doFilter(req, res);
    }
}