package com.campus.Campus.Connect.service.impl;

import com.campus.Campus.Connect.dto.UserRequestDTO;
import com.campus.Campus.Connect.dto.UserResponseDTO;
import com.campus.Campus.Connect.entity.User;
import com.campus.Campus.Connect.enums.Role;
import com.campus.Campus.Connect.exceptions.AccessDeniedException;
import com.campus.Campus.Connect.exceptions.ResourceNotFoundException;
import com.campus.Campus.Connect.exceptions.UserAlreadyExistsException;
import com.campus.Campus.Connect.repository.UserRepository;
import com.campus.Campus.Connect.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        Optional<User> user = userRepository.findByEmail(userRequestDTO.getEmail());
        if (user.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        User newUser = dtoToUser(userRequestDTO);
        userRepository.save(newUser);
        return userToDTO(newUser);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();
        for (User user : users) {
            userResponseDTOS.add(userToDTO(user));
        }
        return userResponseDTOS;
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        return userToDTO(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        userRepository.delete(user);
    }

    @Override
    public UserResponseDTO updateUser(Long userId, UserRequestDTO dto) {
        // SECURITY FIX: Verify identity
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        if (!loggedInUser.getUserId().equals(userId) && loggedInUser.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("You are not authorized to update this profile");
        }

        if (loggedInUser.getRole() != Role.ADMIN && dto.getRole() == Role.ADMIN) {
            throw new AccessDeniedException("You cannot escalate your privileges to ADMIN");
        }

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        if (!dto.getEmail().equals(existingUser.getEmail())) {
            Optional<User> userWithEmail = userRepository.findByEmail(dto.getEmail());
            if (userWithEmail.isPresent()) {
                throw new UserAlreadyExistsException("Email is already linked to another user");
            }
        }

        existingUser.setName(dto.getName());
        existingUser.setRole(dto.getRole());
        existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        existingUser.setEmail(dto.getEmail());

        userRepository.save(existingUser);
        return userToDTO(existingUser);
    }

    private User dtoToUser(UserRequestDTO userRequestDTO) {
        User newUser = new User();
        newUser.setName(userRequestDTO.getName());
        newUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        newUser.setEmail(userRequestDTO.getEmail());
        newUser.setRole(userRequestDTO.getRole());
        return newUser;
    }

    private UserResponseDTO userToDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setUserId(user.getUserId());
        userResponseDTO.setCreatedAt(user.getCreatedAt());
        return userResponseDTO;
    }
}