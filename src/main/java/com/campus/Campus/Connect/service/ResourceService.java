package com.campus.Campus.Connect.service;

import com.campus.Campus.Connect.dto.ResourceFilterDTO;
import com.campus.Campus.Connect.dto.ResourceRequestDTO;
import com.campus.Campus.Connect.dto.ResourceResponseDTO;
import com.campus.Campus.Connect.entity.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResourceService {

    ResourceResponseDTO createResource(ResourceRequestDTO resourceRequestDTO,  MultipartFile file);

    ResourceResponseDTO getResourceById(Long resourceId);

    ResourceResponseDTO updateResource(Long resourceId,
                                       ResourceRequestDTO resourceRequestDTO,  MultipartFile file);

    void deleteResourceById(Long resourceId);

    Page<ResourceResponseDTO> getResources(ResourceFilterDTO resourceFilterDTO, int page, int size, String sortBy, String sortDirection);
}