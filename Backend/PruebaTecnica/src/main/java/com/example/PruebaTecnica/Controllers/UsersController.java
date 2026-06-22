package com.example.PruebaTecnica.Controllers;

import com.example.PruebaTecnica.dto.UserResponseDTO;
import com.example.PruebaTecnica.modelos.Users;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

import com.example.PruebaTecnica.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/pruebaTecnica")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/usuarios")
    public ResponseEntity<?> getUsers(
            @RequestParam(defaultValue = "0")    int page,
            @RequestParam(defaultValue = "10")   int limit,
            @RequestParam(required = false)      String role,
            @RequestParam(required = false)      String status,
            @RequestParam(required = false)      String search) {

        Page<Users> result = usersService.getFiltered(role, status, search, page, limit);

        return ResponseEntity.ok(Map.of(
                "data",       result.getContent().stream().map(UserResponseDTO::new).toList(),
                "totalPages", result.getTotalPages(),
                "totalItems", result.getTotalElements(),
                "page",       page
        ));
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Integer id) {
        return usersService.getById(id)
                .map(user -> ResponseEntity.ok(new UserResponseDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/usuarios")
    public UserResponseDTO create(@Valid @RequestBody Users user) {
        return new UserResponseDTO(usersService.create(user));
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Users> update(@Valid @PathVariable Integer id, @RequestBody Users user) {
        return ResponseEntity.ok(usersService.update(id, user));
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        usersService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
