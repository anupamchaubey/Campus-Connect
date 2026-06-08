package com.campus.Campus.Connect.repository;

import com.campus.Campus.Connect.entity.InterviewExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;

public interface InterviewExperienceRepository extends JpaRepository<InterviewExperience, Long> {
    @EntityGraph(attributePaths = {"postedBy"})
    List<InterviewExperience> findAll();
}