package com.snd.fileupload.controllers;

import com.snd.fileupload.dtos.DrugCreationRequest;
import com.snd.fileupload.dtos.DrugInfoDto;
import com.snd.fileupload.exceptions.DrugNotFoundException;
import com.snd.fileupload.models.*;
import com.snd.fileupload.repositories.DrugRepository;
import com.snd.fileupload.repositories.ImageRepository;
import com.snd.fileupload.utils.DrugMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/drugs")
public class DrugController {
    private final DrugRepository drugRepository;
    private final ImageRepository imageRepository;

    public DrugController(DrugRepository drugRepository, ImageRepository imageRepository) {
        this.drugRepository = drugRepository;
        this.imageRepository = imageRepository;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<DrugInfoDto> addNewDrug(@RequestBody DrugCreationRequest request) {
        Drug drug = DrugMapper.toEntity(request);
        User user = new User("moaz", UserRole.ADMIN);
        drug.setCreatedBy(user);
        drug = drugRepository.save(drug);
        return ResponseEntity.ok(DrugMapper.toInfo(drug));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrugInfoDto> getDrugInfo(@PathVariable("id") Integer id) {
        Optional<Drug> drug = drugRepository.findById(id);
        if (drug.isEmpty()) {
            throw new DrugNotFoundException();
        }
        return ResponseEntity.ok(DrugMapper.toInfo(drug.get()));
    }

    @PatchMapping("/status")
    public ResponseEntity<DrugInfoDto> setDrugStatus(@RequestParam("id") Integer id,
                                                     @RequestParam("status") DrugStatus status) {
        Optional<Drug> drugOptional = drugRepository.getDrugById(id);
        if (drugOptional.isEmpty()) {
            throw new DrugNotFoundException();
        }
        Drug drug = drugOptional.get();
        drug.setStatus(status);
        return ResponseEntity.ok(DrugMapper.toInfo(drug));
    }

    @GetMapping
    public ResponseEntity<List<Drug>> getAllDrugs(
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "form", required = false) String form,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) DrugStatus status,
            @RequestParam(value = "user", required = false) String username,
            @RequestParam(value = "imaged", required = false) Boolean isolate) {
        Pageable request = PageRequest.of(page, size);
        List<Drug> drugsList = drugRepository.filterDrugs(status, name, form, username, isolate, request).getContent();
        if (drugsList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(drugsList);
    }

    @GetMapping("/no-images")
    public ResponseEntity<List<Drug>> getNoImageDrugs(
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "10") Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Drug> drugs = drugRepository.findAllByImagesIsEmpty(pageable);
        if (drugs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(drugs.getContent());
    }

    @GetMapping("/images")
    public ResponseEntity<List<Image>> getDrugImages(@RequestParam("drugId") Integer id) {
        Optional<Drug> d = drugRepository.getDrugById(id);
        if (d.isEmpty()) {
            throw new DrugNotFoundException();
        }
        List<Image> images = d.get().getImages();
        if (images.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(images);
    }

}
