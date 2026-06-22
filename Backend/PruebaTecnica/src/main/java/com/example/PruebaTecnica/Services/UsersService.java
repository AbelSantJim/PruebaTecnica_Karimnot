package com.example.PruebaTecnica.Services;

import com.example.PruebaTecnica.modelos.Address;
import com.example.PruebaTecnica.modelos.Users;
import com.example.PruebaTecnica.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository usersRepository;

    public List<Users> getAll() {
        return usersRepository.findAll();
    }

    public Optional<Users> getById(Integer id) {
        return usersRepository.findById(id);
    }

    public Users create(Users user) {
        // Hashear password antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    public Users update(Integer id, Users updatedUser) {
        Users existing = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        existing.setFirstName(updatedUser.getFirstName());
        existing.setLastName(updatedUser.getLastName());
        existing.setEmail(updatedUser.getEmail());
        existing.setPhoneNumber(updatedUser.getPhoneNumber());
        existing.setRole(updatedUser.getRole());
        existing.setStatus(updatedUser.getStatus());
        existing.setProfilePicture(updatedUser.getProfilePicture());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        // ← Solo actualizar address si viene con datos reales
        if (updatedUser.getAddress() != null) {
            Address addr = updatedUser.getAddress();
            boolean hasData = addr.getStreet()     != null && !addr.getStreet().isBlank()
                    || addr.getCity()       != null && !addr.getCity().isBlank()
                    || addr.getNumber()     != null && !addr.getNumber().isBlank()
                    || addr.getPostalCode() != null && !addr.getPostalCode().isBlank();
            if (hasData) {
                existing.setAddress(addr);
            }
        }

        return usersRepository.save(existing);
    }

    public void delete(Integer id) {
        usersRepository.deleteById(id);
    }


    public Page<Users> getFiltered(String role, String status, String search, int page, int limit) {
        return usersRepository.findWithFilters(role, status, search, PageRequest.of(page, limit));
    }
}