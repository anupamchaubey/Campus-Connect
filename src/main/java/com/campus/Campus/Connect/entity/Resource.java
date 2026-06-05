package com.campus.Campus.Connect.entity;

import com.campus.Campus.Connect.enums.ResourceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="resources")
@Getter
@Setter
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resourceId;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResourceType resourceType;

    @Column(nullable = false, length = 1000)
    private String fileUrl;

    private String semester;

    private String branch;

    @Column(nullable = false)
    private String subject;

    private String college;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="uploaded_by")
    private User uploader;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
