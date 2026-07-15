package com.bank.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.auth_service.dto.LoginRequest;
import com.bank.auth_service.dto.RegisterRequest;
import com.bank.auth_service.entity.User;
import com.bank.auth_service.repository.UserRepository;
import com.bank.auth_service.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ✅ REGISTER
    public String register(RegisterRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        return "User Registered ✅";
    }

    // ✅ LOGIN WITH JWT
    public String login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            return "User not found ❌";
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return "Invalid password ❌";
        }

        // ✅ Generate JWT
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return token;
    }
}