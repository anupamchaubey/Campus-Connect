package com.campus.Campus.Connect.entity;

import com.campus.Campus.Connect.enums.Difficulty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="interviewExperiences")
@Getter
@Setter
public class InterviewExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interviewExperienceId;

    private String companyName;
    private String jobRole;
    private String description;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    // ADD THIS: Missing relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="posted_by", nullable = false)
    private User postedBy;

    private LocalDateTime createdAt;

    @PrePersist
    private void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }
}