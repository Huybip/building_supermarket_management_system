package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.dto.AuthResponse;
import com.example.servingwebcontent.dto.LoginRequest;
import com.example.servingwebcontent.dto.UserCreateDTO;
import com.example.servingwebcontent.model.User;
import com.example.servingwebcontent.service.UserService;
import com.example.servingwebcontent.security.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwt;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwt, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwt = jwt;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserCreateDTO dto) {
        User saved = userService.createFromDto(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        String token = jwt.generateToken(auth.getName());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
