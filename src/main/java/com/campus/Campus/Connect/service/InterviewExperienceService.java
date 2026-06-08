package com.campus.Campus.Connect.service;

import com.campus.Campus.Connect.dto.InterviewExperienceRequestDTO;
import com.campus.Campus.Connect.dto.InterviewExperienceResponseDTO;
import java.util.List;

public interface InterviewExperienceService {
    InterviewExperienceResponseDTO createExperience(InterviewExperienceRequestDTO dto);
    InterviewExperienceResponseDTO getExperienceById(Long id);
    List<InterviewExperienceResponseDTO> getAllExperiences();
    void deleteExperience(Long id);
}