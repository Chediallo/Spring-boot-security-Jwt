/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.securityjwt.controller;

import com.securityjwt.entity.Role;
import com.securityjwt.entity.User;
import com.securityjwt.jwt.JwtTokenProvider;
import com.securityjwt.payload.JwtAuthResponse;
import com.securityjwt.payload.Login;
import com.securityjwt.payload.SignUp;
import com.securityjwt.repository.RoleRepository;
import com.securityjwt.repository.UserRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mohamed
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    // Dépendance pour se connecter seulement le Authentication manager
    @PostMapping(value = {"/signin", "/login"})
    public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody Login login){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsernameOrEmail(),
                        login.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // recuperer le token depuis le TokenProvider
        String token = jwtTokenProvider.generateToken(authentication);
        
        JwtAuthResponse authResponse = new JwtAuthResponse(token);
        
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
    
    
    @PostMapping(value = {"/signup","/register"})
    public ResponseEntity<?> registerUser(@RequestBody SignUp signUp){
        
        if(userRepository.existsByUsername(signUp.getUsername())){
            return new ResponseEntity<>("Username is already taken.!", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(signUp.getEmail())){
            return new ResponseEntity<>("Email is already taken.!", HttpStatus.BAD_REQUEST);
        }
        
        User user = new User();
        user.setPrenom(signUp.getPrenom());
        user.setUsername(signUp.getUsername());
        user.setEmail(signUp.getEmail());
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));
        
        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        
        userRepository.save(user);
        
        return new ResponseEntity<>("User registered succesfully.!", HttpStatus.OK);
    }
}
