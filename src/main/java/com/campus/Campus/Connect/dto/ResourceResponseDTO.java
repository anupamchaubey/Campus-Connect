package com.campus.Campus.Connect.dto;

import com.campus.Campus.Connect.enums.ResourceType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResourceResponseDTO {

    private Long resourceId;

    private String title;

    private String description;

    private ResourceType resourceType;

    private String fileUrl;

    private String semester;

    private String branch;

    private String subject;

    private String college;

    private Long uploaderId;

    private String uploaderName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
