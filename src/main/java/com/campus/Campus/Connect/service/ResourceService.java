package com.campus.Campus.Connect.service;

import com.campus.Campus.Connect.dto.ResourceFilterDTO;
import com.campus.Campus.Connect.dto.ResourceRequestDTO;
import com.campus.Campus.Connect.dto.ResourceResponseDTO;
import com.campus.Campus.Connect.entity.Resource;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ResourceService {

    ResourceResponseDTO createResource(ResourceRequestDTO resourceRequestDTO);

    ResourceResponseDTO getResourceById(Long resourceId);

    List<ResourceResponseDTO> getAllResources();

    ResourceResponseDTO updateResource(Long resourceId,
                                       ResourceRequestDTO resourceRequestDTO);

    void deleteResourceById(Long resourceId);

    List<ResourceResponseDTO> getResources(ResourceFilterDTO resourceFilterDTO);
}