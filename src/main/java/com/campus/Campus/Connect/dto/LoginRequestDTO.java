package com.campus.Campus.Connect.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @Email(message = "invalid email")
    @NotBlank(message = "enter email")
    private String email;

    @Size(min = 6, max = 20, message = "invalid password")
    private String password;
}
