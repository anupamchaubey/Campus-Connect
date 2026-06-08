package com.campus.Campus.Connect.service.impl;

import com.campus.Campus.Connect.dto.InterviewExperienceRequestDTO;
import com.campus.Campus.Connect.dto.InterviewExperienceResponseDTO;
import com.campus.Campus.Connect.entity.InterviewExperience;
import com.campus.Campus.Connect.entity.User;
import com.campus.Campus.Connect.enums.Role;
import com.campus.Campus.Connect.exceptions.AccessDeniedException;
import com.campus.Campus.Connect.exceptions.ResourceNotFoundException;
import com.campus.Campus.Connect.repository.InterviewExperienceRepository;
import com.campus.Campus.Connect.repository.UserRepository;
import com.campus.Campus.Connect.service.InterviewExperienceService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InterviewExperienceServiceImpl implements InterviewExperienceService {

    private final InterviewExperienceRepository repository;
    private final UserRepository userRepository;

    public InterviewExperienceServiceImpl(InterviewExperienceRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private InterviewExperienceResponseDTO mapToDTO(InterviewExperience entity) {
        InterviewExperienceResponseDTO dto = new InterviewExperienceResponseDTO();
        dto.setInterviewExperienceId(entity.getInterviewExperienceId());
        dto.setCompanyName(entity.getCompanyName());
        dto.setJobRole(entity.getJobRole());
        dto.setDescription(entity.getDescription());
        dto.setDifficulty(entity.getDifficulty());
        dto.setCreatedAt(entity.getCreatedAt());

        if (entity.getPostedBy() != null) {
            dto.setPostedById(entity.getPostedBy().getUserId());
            dto.setPostedByName(entity.getPostedBy().getName());
        }
        return dto;
    }

    @Override
    public InterviewExperienceResponseDTO createExperience(InterviewExperienceRequestDTO dto) {
        InterviewExperience experience = new InterviewExperience();
        experience.setCompanyName(dto.getCompanyName());
        experience.setJobRole(dto.getJobRole());
        experience.setDescription(dto.getDescription());
        experience.setDifficulty(dto.getDifficulty());
        experience.setPostedBy(getCurrentUser());

        return mapToDTO(repository.save(experience));
    }

    @Override
    public InterviewExperienceResponseDTO getExperienceById(Long id) {
        InterviewExperience experience = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found"));
        return mapToDTO(experience);
    }

    @Override
    public List<InterviewExperienceResponseDTO> getAllExperiences() {
        return repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteExperience(Long id) {
        InterviewExperience experience = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Experience not found"));

        User loggedInUser = getCurrentUser();
        if (!experience.getPostedBy().getUserId().equals(loggedInUser.getUserId()) && loggedInUser.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("You are not allowed to delete this post");
        }
        repository.delete(experience);
    }
}