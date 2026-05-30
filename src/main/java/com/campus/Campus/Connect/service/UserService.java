package com.campus.Campus.Connect.service;

import com.campus.Campus.Connect.dto.UserRequestDTO;
import com.campus.Campus.Connect.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long userId);

    void deleteUser(Long userId);

    UserResponseDTO updateUser(Long userId, UserRequestDTO dto);
}
