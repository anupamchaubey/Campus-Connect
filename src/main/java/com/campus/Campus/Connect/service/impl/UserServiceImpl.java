package com.campus.Campus.Connect.service.impl;

import com.campus.Campus.Connect.dto.UserRequestDTO;
import com.campus.Campus.Connect.dto.UserResponseDTO;
import com.campus.Campus.Connect.entity.User;
import com.campus.Campus.Connect.exceptions.ResourceNotFoundException;
import com.campus.Campus.Connect.exceptions.UserAlreadyExistsException;
import com.campus.Campus.Connect.repository.UserRepository;
import com.campus.Campus.Connect.service.UserService;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        Optional<User> user=userRepository.findByEmail(userRequestDTO.getEmail());
        if(user.isPresent()){
            throw new UserAlreadyExistsException("User already exists");
        }
        User newUser=new User();
        newUser.setName(userRequestDTO.getName());
        newUser.setPassword(userRequestDTO.getPassword());
        newUser.setEmail(userRequestDTO.getEmail());
        newUser.setRole(userRequestDTO.getRole());

        userRepository.save(newUser);
        UserResponseDTO userResponseDTO=userToDTO(newUser);
        return userResponseDTO;
    }


    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users=userRepository.findAll();
        List<UserResponseDTO> userResponseDTOS=new ArrayList<>();
        for(User user:users){
            UserResponseDTO userResponseDTO=userToDTO(user);
            userResponseDTOS.add(userResponseDTO);
        }
        return userResponseDTOS;
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {
        User user=userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        return userToDTO(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user=userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        userRepository.delete(user);
    }

    @Override
    public UserResponseDTO updateUser(Long userId, UserRequestDTO dto) {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

        if (!dto.getEmail().equals(existingUser.getEmail())) {

            Optional<User> userWithEmail =
                    userRepository.findByEmail(dto.getEmail());

            if (userWithEmail.isPresent()) {
                throw new UserAlreadyExistsException(
                        "Email is already linked to another user");
            }
        }

        existingUser.setName(dto.getName());
        existingUser.setRole(dto.getRole());
        existingUser.setPassword(dto.getPassword());
        existingUser.setEmail(dto.getEmail());

        userRepository.save(existingUser);

        return userToDTO(existingUser);
    }

    // DTO mapper functions

    private UserResponseDTO userToDTO(User user){
        UserResponseDTO userResponseDTO=new UserResponseDTO();
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setUserId(user.getUserId());
        userResponseDTO.setCreatedAt(user.getCreatedAt());
        return userResponseDTO;
    }
}