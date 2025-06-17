package com.example.jwt_demo.controller;


import com.example.jwt_demo.jwt.JwtUtil;
import com.example.jwt_demo.model.User;
import com.example.jwt_demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (userRepository.findByUsername(username) != null)
            return Map.of("error", "Username exists");
        User user = new User(username, passwordEncoder.encode(password));
        userRepository.save(user);
        return Map.of("result", "User registered");
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        var user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword()))
            return Map.of("error", "Bad credentials");
        String token = jwtUtil.generateToken(username);
        return Map.of("token", token);
    }
}
