package com.campus.Campus.Connect.dto;

import com.campus.Campus.Connect.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    @NotBlank(message = "name can not be empty")
    String name;

    @Email(message = "email is not valid")
    String email;

    @Size(min = 6, max = 100, message = "password should be between 6 to 100 length")
    String password;

    @NotNull(message = "select the role")
    Role role;

}
