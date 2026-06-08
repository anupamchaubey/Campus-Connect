package com.campus.Campus.Connect.dto;

import com.campus.Campus.Connect.enums.Difficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewExperienceRequestDTO {
    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Job role is required")
    private String jobRole;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Difficulty must be provided")
    private Difficulty difficulty;
}