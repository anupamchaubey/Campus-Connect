package com.campus.Campus.Connect.dto;

import com.campus.Campus.Connect.enums.ResourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResourceRequestDTO {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private ResourceType resourceType;

    @NotBlank
    private String fileUrl;

    private String semester;

    @NotBlank
    private String branch;

    @NotBlank
    private String subject;

    private String college;

}
