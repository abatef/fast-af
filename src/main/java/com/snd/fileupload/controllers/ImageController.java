package com.snd.fileupload.controllers;

import com.snd.fileupload.dtos.FileUploadResponse;
import com.snd.fileupload.exceptions.DrugNotFoundException;
import com.snd.fileupload.models.Drug;
import com.snd.fileupload.models.Image;
import com.snd.fileupload.models.User;
import com.snd.fileupload.models.UserRole;
import com.snd.fileupload.repositories.DrugRepository;
import com.snd.fileupload.repositories.ImageRepository;
import com.snd.fileupload.repositories.UserRepository;
import com.snd.fileupload.services.StorageService;
import com.snd.fileupload.dtos.UploadStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final StorageService storageService;
    private final DrugRepository drugRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public ImageController(StorageService storageService, DrugRepository drugRepository,
                           ImageRepository imageRepository, UserRepository userRepository) {
        this.storageService = storageService;
        this.drugRepository = drugRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file,
                                                         @RequestParam("drugID") String drugId,
                                                         @RequestParam("user") String username) throws IOException {
        User user = userRepository.getUserByUsername(username);
        FileUploadResponse fileUploadResponse = storageService.upload(file);
        if (fileUploadResponse.getStatus() == UploadStatus.SUCCESS) {
            Optional<Drug> drugOptional = drugRepository.findById(Integer.parseInt(drugId));
            if (drugOptional.isEmpty()) {
                throw new DrugNotFoundException();
            }
            Drug drug = drugOptional.get();
            Image image = new Image();
            image.setCreatedBy(user);
            image.setUrl(fileUploadResponse.getFileUrl());
            image.setDrug(drug);
            imageRepository.save(image);
            return ResponseEntity.status(HttpStatus.CREATED).body(fileUploadResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fileUploadResponse);
    }

    @GetMapping
    public List<String> getAllImages(@RequestParam("id") String drugId) {
        List<Image> images = imageRepository.findImageByDrug_Id(Integer.parseInt(drugId));
        return images.stream().map(Image::getUrl).collect(Collectors.toList());
    }
}
