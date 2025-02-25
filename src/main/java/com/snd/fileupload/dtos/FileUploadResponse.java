package com.snd.fileupload.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadResponse {
    private String fileName;
    private String fileUrl;
    private String fileType;
    private String fileSize;
    private String message;
    private UploadStatus status;
}
