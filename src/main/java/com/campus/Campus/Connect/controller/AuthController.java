package com.campus.Campus.Connect.controller;

import com.campus.Campus.Connect.dto.LoginRequestDTO;
import com.campus.Campus.Connect.dto.LoginResponseDTO;
import com.campus.Campus.Connect.dto.UserRequestDTO;
import com.campus.Campus.Connect.dto.UserResponseDTO;
import com.campus.Campus.Connect.security.JwtService;
import com.campus.Campus.Connect.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authManager, JwtService jwtService) {

        this.userService = userService;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getEmail(),
                        loginRequestDTO.getPassword()
                )
        );

        String token =
                jwtService.generateToken(loginRequestDTO.getEmail());

        LoginResponseDTO response =
                new LoginResponseDTO();

        response.setMessage("Login successful");
        response.setToken(token);

        return response;
    }

    @PostMapping("/register")
    public UserResponseDTO register(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return userService.createUser(userRequestDTO);
    }
}
