package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.User;
import com.example.servingwebcontent.model.Role;
import com.example.servingwebcontent.repository.UserRepository;
import com.example.servingwebcontent.dto.UserCreateDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository repo, BCryptPasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public Optional<User> findById(Long id) {
        return repo.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Transactional
    public User createFromDto(UserCreateDTO dto) {
        if (repo.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        User u = new User();
        u.setUsername(dto.getUsername());
        u.setPassword(encoder.encode(dto.getPassword()));
        try {
            u.setRole(Role.valueOf(dto.getRole()));
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid role");
        }
        u.setFullName(dto.getFullName());
        u.setPhone(dto.getPhone());
        u.setAddress(dto.getAddress());
        return repo.save(u);
    }

    @Transactional
    public User update(Long id, UserCreateDTO dto) {
        return repo.findById(id).map(existing -> {
            existing.setFullName(dto.getFullName());
            existing.setPhone(dto.getPhone());
            existing.setAddress(dto.getAddress());
            // role change only if valid
            if (dto.getRole() != null) {
                try {
                    existing.setRole(Role.valueOf(dto.getRole()));
                } catch (Exception e) {
                }
            }
            // password change: only if provided
            if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
                existing.setPassword(encoder.encode(dto.getPassword()));
            }
            return repo.save(existing);
        }).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Transactional
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
