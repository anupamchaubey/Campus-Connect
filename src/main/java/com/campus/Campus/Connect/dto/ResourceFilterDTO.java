package com.campus.Campus.Connect.dto;

import com.campus.Campus.Connect.enums.ResourceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceFilterDTO {

    private String keyword;

    private String subject;

    private ResourceType resourceType;

    private String college;

    private String semester;

    private String branch;
}
