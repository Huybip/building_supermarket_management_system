package com.example.servingwebcontent.service;

import com.example.servingwebcontent.dto.UserCreateDTO;
import com.example.servingwebcontent.model.User;
import com.example.servingwebcontent.model.Role;
import com.example.servingwebcontent.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Test
    void createHashesPasswordAndSaves() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
        UserService svc = new UserService(repo, enc);

        UserCreateDTO dto = new UserCreateDTO();
        dto.setUsername("u1");
        dto.setPassword("secret");
        dto.setRole("CUSTOMER");

        when(repo.findByUsername("u1")).thenReturn(Optional.empty());
        when(repo.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User saved = svc.createFromDto(dto);
        assertEquals("u1", saved.getUsername());
        assertNotEquals("secret", saved.getPassword());
        assertEquals(Role.CUSTOMER, saved.getRole());
        assertTrue(enc.matches("secret", saved.getPassword()));
    }
}
