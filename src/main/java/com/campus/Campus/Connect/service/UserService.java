package com.campus.Campus.Connect.service;

import com.campus.Campus.Connect.dto.UserRequestDTO;
import com.campus.Campus.Connect.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO) throws Exception;

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long userId) throws Exception;

    void deleteUser(Long userId) throws Exception;

    UserResponseDTO updateUser(Long userId, UserRequestDTO dto) throws Exception;
}
