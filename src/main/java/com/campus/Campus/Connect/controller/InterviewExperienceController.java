package com.campus.Campus.Connect.controller;

import com.campus.Campus.Connect.dto.InterviewExperienceRequestDTO;
import com.campus.Campus.Connect.dto.InterviewExperienceResponseDTO;
import com.campus.Campus.Connect.service.InterviewExperienceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviews")
public class InterviewExperienceController {

    private final InterviewExperienceService service;

    public InterviewExperienceController(InterviewExperienceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InterviewExperienceResponseDTO> create(@Valid @RequestBody InterviewExperienceRequestDTO dto) {
        return ResponseEntity.ok(service.createExperience(dto));
    }

    @GetMapping
    public ResponseEntity<List<InterviewExperienceResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllExperiences());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewExperienceResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getExperienceById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteExperience(id);
        return ResponseEntity.noContent().build();
    }
}