package com.example.PruebaTecnica.Controllers;

import com.example.PruebaTecnica.modelos.Users;
import com.example.PruebaTecnica.Repositories.UsersRepository;
import com.example.PruebaTecnica.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin (origins="http://localhost:4200")
@RestController
@RequestMapping("/api/pruebaTecnica")
public class AuthController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // ✅ Generar token con el rol del usuario
        String token = jwtUtil.generateToken(email, user.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("email", email);
        response.put("role", user.getRole());

        return response;
    }
}