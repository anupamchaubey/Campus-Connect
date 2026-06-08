package com.campus.Campus.Connect.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileUploadResponse {

    private String fileUrl;
    private String publicId;
}