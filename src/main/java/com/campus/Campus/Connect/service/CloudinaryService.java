package com.campus.Campus.Connect.service;

import com.campus.Campus.Connect.dto.FileUploadResponse;
import com.campus.Campus.Connect.exceptions.FileUploadException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private static final long MAX_FILE_SIZE =
            10 * 1024 * 1024;

    private final Cloudinary cloudinary;

    public FileUploadResponse uploadFile(
            MultipartFile file
    ) {

        try {
            validateFile(file);
            Map<?, ?> result =
                    cloudinary.uploader().upload(
                            file.getBytes(),
                            ObjectUtils.asMap(
                                    "resource_type", "raw",
                                    "use_filename", true,
                                    "unique_filename", true
                            )
                    );

            return new FileUploadResponse(
                    result.get("secure_url").toString(),
                    result.get("public_id").toString()
            );

        } catch (Exception ex) {

            ex.printStackTrace();

            throw new FileUploadException(
                    ex.getMessage()
            );
        }
    }

    public void deleteFile(String publicId) {

        try {

            cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.asMap(
                            "resource_type", "raw"
                    )
            );

        } catch (IOException ex) {

            throw new FileUploadException(
                    "Failed to delete file"
            );
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileUploadException("File cannot be empty");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new FileUploadException("File size exceeds 10 MB");
        }

        // Allow PDFs, Images, and basic documents
        String contentType = file.getContentType();
        if (contentType == null || !(
                contentType.equals("application/pdf") ||
                        contentType.startsWith("image/") ||
                        contentType.equals("application/zip"))) {
            throw new FileUploadException("Only PDFs, images, and ZIP files are allowed");
        }
    }
}