package com.campus.Campus.Connect.dto;

import com.campus.Campus.Connect.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class InterviewExperienceResponseDTO {
    private Long interviewExperienceId;
    private String companyName;
    private String jobRole;
    private String description;
    private Difficulty difficulty;
    private Long postedById;
    private String postedByName;
    private LocalDateTime createdAt;
}