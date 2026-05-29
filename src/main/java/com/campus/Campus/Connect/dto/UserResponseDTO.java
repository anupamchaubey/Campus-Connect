package com.campus.Campus.Connect.dto;

import com.campus.Campus.Connect.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDTO {

    Long userId;

    String name;

    String email;

    Role role;

    LocalDateTime createdAt;

}
