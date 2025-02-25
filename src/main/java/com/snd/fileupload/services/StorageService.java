package com.snd.fileupload.services;

import com.snd.fileupload.dtos.FileUploadResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface StorageService {
    FileUploadResponse upload(MultipartFile file) throws IOException;
}
