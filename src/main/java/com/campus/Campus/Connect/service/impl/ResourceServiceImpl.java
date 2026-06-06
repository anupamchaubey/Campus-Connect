package com.campus.Campus.Connect.service.impl;

import com.campus.Campus.Connect.dto.ResourceFilterDTO;
import com.campus.Campus.Connect.dto.ResourceRequestDTO;import com.campus.Campus.Connect.dto.ResourceResponseDTO;import com.campus.Campus.Connect.entity.Resource;import com.campus.Campus.Connect.entity.User;import com.campus.Campus.Connect.enums.Role;import com.campus.Campus.Connect.exceptions.AccessDeniedException;import com.campus.Campus.Connect.exceptions.ResourceNotFoundException;import com.campus.Campus.Connect.repository.ResourceRepository;import com.campus.Campus.Connect.repository.UserRepository;import com.campus.Campus.Connect.service.ResourceService;
import com.campus.Campus.Connect.specification.ResourceSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final UserRepository userRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository, UserRepository userRepository) {
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
    }

    private Resource resourceRequestDTOToResource(ResourceRequestDTO dto) {

        Resource resource = new Resource();

        resource.setTitle(dto.getTitle());
        resource.setDescription(dto.getDescription());
        resource.setResourceType(dto.getResourceType());
        resource.setFileUrl(dto.getFileUrl());
        resource.setSemester(dto.getSemester());
        resource.setBranch(dto.getBranch());
        resource.setSubject(dto.getSubject());
        resource.setCollege(dto.getCollege());
        return resource;
    }

    private ResourceResponseDTO resourceToResourceResponseDTO(Resource resource) {

        ResourceResponseDTO dto = new ResourceResponseDTO();

        dto.setResourceId(resource.getResourceId());
        dto.setTitle(resource.getTitle());
        dto.setDescription(resource.getDescription());
        dto.setResourceType(resource.getResourceType());
        dto.setFileUrl(resource.getFileUrl());
        dto.setSemester(resource.getSemester());
        dto.setBranch(resource.getBranch());
        dto.setSubject(resource.getSubject());
        dto.setCollege(resource.getCollege());

        if (resource.getUploader() != null) {
            dto.setUploaderId(resource.getUploader().getUserId());
            dto.setUploaderName(resource.getUploader().getName());
        }

        dto.setCreatedAt(resource.getCreatedAt());
        dto.setUpdatedAt(resource.getUpdatedAt());

        return dto;
    }

    @Override
    public ResourceResponseDTO createResource(
            ResourceRequestDTO resourceRequestDTO) {

        Resource resource = resourceRequestDTOToResource(resourceRequestDTO);
        resource.setUploader(getCurrentUser());
        Resource savedResource = resourceRepository.save(resource);

        return resourceToResourceResponseDTO(savedResource);
    }

    @Override
    public ResourceResponseDTO getResourceById(Long resourceId) {

        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Resource not found with id: " + resourceId));

        return resourceToResourceResponseDTO(resource);
    }


    @Override
    public ResourceResponseDTO updateResource(
            Long resourceId,
            ResourceRequestDTO resourceRequestDTO) {

        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Resource not found with id: " + resourceId));

        User resourceUser = resource.getUploader();
        User loggedInUser = getCurrentUser();

        if(!(resourceUser.getUserId().equals(loggedInUser.getUserId()) || loggedInUser.getRole() == Role.ADMIN)) {
            throw new AccessDeniedException("You are not authorized to perform this action.");
        }

        resource.setTitle(resourceRequestDTO.getTitle());
        resource.setDescription(resourceRequestDTO.getDescription());
        resource.setResourceType(resourceRequestDTO.getResourceType());
        resource.setFileUrl(resourceRequestDTO.getFileUrl());
        resource.setSemester(resourceRequestDTO.getSemester());
        resource.setBranch(resourceRequestDTO.getBranch());
        resource.setSubject(resourceRequestDTO.getSubject());
        resource.setCollege(resourceRequestDTO.getCollege());

        Resource updatedResource = resourceRepository.save(resource);

        return resourceToResourceResponseDTO(updatedResource);
    }

    @Override
    public void deleteResourceById(Long resourceId) {

        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Resource not found with id: " + resourceId));
        User resourceUser = resource.getUploader();

        User loggedInUser = getCurrentUser();
        if (resourceUser.getUserId().equals(loggedInUser.getUserId()) || loggedInUser.getRole() == Role.ADMIN) {
            resourceRepository.delete(resource);
        } else throw new AccessDeniedException("You are not allowed to perform this action");
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }
    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of(
                    "createdAt",
                    "updatedAt",
                    "title",
                    "subject",
                    "resourceType"
            );

    @Override
    public Page<ResourceResponseDTO> getResources(ResourceFilterDTO resourceFilterDTO, int page, int size, String sortBy, String sortDirection) {
        Specification<Resource> spec=ResourceSpecification.filterResources(resourceFilterDTO);
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new IllegalArgumentException(
                    "Invalid sort field"
            );
        }
        Sort sort;
        if(sortDirection.equalsIgnoreCase("asc")){
            sort= Sort.by(sortBy).ascending();
        }else{
            sort= Sort.by(sortBy).descending();
        }
        Pageable pageable= PageRequest.of(page, size, sort);
        Page<Resource> resources = resourceRepository.findAll(spec, pageable);

        return resources.map(
                resource -> resourceToResourceResponseDTO(resource)
        );
    }
}