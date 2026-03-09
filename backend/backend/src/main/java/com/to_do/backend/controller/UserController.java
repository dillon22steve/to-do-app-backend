package com.to_do.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.to_do.backend.repository.UserRepository;
import com.to_do.backend.model.User;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // SIGN UP: Create a new user
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        System.out.println("Attempting to register user: " + user.getUsername());
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        
        // In a real app: user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        System.out.println("Login attempt for username: " + loginRequest.getUsername());
        return userRepository.findByUsername(loginRequest.getUsername())
            .filter(user -> user.getPassword().equals(loginRequest.getPassword()))
            .map(user -> {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Login successful");
                response.put("username", user.getUsername());
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("message", "Invalid username or password")));
    }
    
}
