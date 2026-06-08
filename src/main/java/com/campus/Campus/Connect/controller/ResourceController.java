package com.campus.Campus.Connect.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.campus.Campus.Connect.dto.ResourceFilterDTO;
import com.campus.Campus.Connect.dto.ResourceRequestDTO;
import com.campus.Campus.Connect.dto.ResourceResponseDTO;
import com.campus.Campus.Connect.entity.Resource;
import com.campus.Campus.Connect.service.ResourceService;
import com.campus.Campus.Connect.specification.ResourceSpecification;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;
    private final ObjectMapper objectMapper;

    public ResourceController(
            ResourceService resourceService,
            ObjectMapper objectMapper) {

        this.resourceService = resourceService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResourceResponseDTO> create(
            @RequestPart("resource") String resourceJson,
            @RequestPart("file") MultipartFile file) throws Exception {

        ResourceRequestDTO dto =
                objectMapper.readValue(
                        resourceJson,
                        ResourceRequestDTO.class
                );

        return ResponseEntity.ok(
                resourceService.createResource(dto, file)
        );
    }

    @GetMapping("/{id}")
    public ResourceResponseDTO getResource(@PathVariable Long id) {
        return resourceService.getResourceById(id);
    }

    @PutMapping(
            value = "/{resourceId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResourceResponseDTO updateResource(
            @PathVariable Long resourceId,
            @RequestPart("resource") String resourceJson,
            @RequestPart(value = "file", required = false)
            MultipartFile file
    ) throws JsonProcessingException {
        ResourceRequestDTO dto =
                objectMapper.readValue(
                        resourceJson,
                        ResourceRequestDTO.class
                );

        return resourceService.updateResource(
                resourceId,
                dto,
                file
        );
    }

    @DeleteMapping("/{resourceId}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long resourceId) {
        resourceService.deleteResourceById(resourceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public Page<ResourceResponseDTO> getResources(ResourceFilterDTO resourceFilterDTO,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "createdAt") String sortBy,
                                                  @RequestParam(defaultValue = "desc") String sortDirection ) {
        return resourceService.getResources(resourceFilterDTO, page, size, sortBy, sortDirection);
    }
}
