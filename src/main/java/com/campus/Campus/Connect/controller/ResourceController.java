package com.campus.Campus.Connect.controller;

import com.campus.Campus.Connect.dto.ResourceFilterDTO;
import com.campus.Campus.Connect.dto.ResourceRequestDTO;
import com.campus.Campus.Connect.dto.ResourceResponseDTO;
import com.campus.Campus.Connect.entity.Resource;
import com.campus.Campus.Connect.service.ResourceService;
import com.campus.Campus.Connect.specification.ResourceSpecification;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping
    public ResourceResponseDTO create(@RequestBody @Valid ResourceRequestDTO dto) {
        return resourceService.createResource(dto);
    }

    @GetMapping("/{id}")
    public ResourceResponseDTO getResource(@PathVariable Long id) {
        return resourceService.getResourceById(id);
    }

    @PutMapping("/{resourceId}")
    public ResourceResponseDTO updateResource(@PathVariable Long resourceId ,@RequestBody @Valid ResourceRequestDTO dto) {
        return resourceService.updateResource(resourceId, dto);
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
