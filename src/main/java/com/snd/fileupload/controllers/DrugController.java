package com.snd.fileupload.controllers;

import com.snd.fileupload.dtos.DrugCreationRequest;
import com.snd.fileupload.dtos.DrugInfoDto;
import com.snd.fileupload.dtos.ImageDTO;
import com.snd.fileupload.exceptions.DrugNotFoundException;
import com.snd.fileupload.models.*;
import com.snd.fileupload.repositories.DrugRepository;
import com.snd.fileupload.repositories.ImageRepository;
import com.snd.fileupload.utils.DrugMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/drugs")
public class DrugController {
    private final DrugRepository drugRepository;
    private final ImageRepository imageRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public DrugController(DrugRepository drugRepository, ImageRepository imageRepository) {
        this.drugRepository = drugRepository;
        this.imageRepository = imageRepository;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<DrugInfoDto> addNewDrug(@RequestBody DrugCreationRequest request) {
        logger.info("DRUG TO ENTITY");
        Drug drug = DrugMapper.toEntity(request);
        User user = new User("moaz", UserRole.ADMIN);
        drug.setCreatedBy(user);
        drug = drugRepository.save(drug);
        logger.info("DRUG ID: {}", drug.getId());
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

    @Transactional
    @PatchMapping("/status")
    public ResponseEntity<DrugInfoDto> setDrugStatus(@RequestParam("id") Integer id,
                                                     @RequestParam("status") DrugStatus status) {
        Optional<Drug> drugOptional = drugRepository.getDrugById(id);
        if (drugOptional.isEmpty()) {
            throw new DrugNotFoundException();
        }
        Drug drug = drugOptional.get();
        drug.setStatus(status);
        drugRepository.save(drug);
        return ResponseEntity.ok(DrugMapper.toInfo(drug));
    }

    @Transactional
    @PatchMapping("/status/bulk")
    public ResponseEntity<List<DrugInfoDto>> setDrugStatusBulk(@RequestParam(value = "status") DrugStatus status,
                                                               @RequestBody List<Integer> ids) {
        List<DrugInfoDto> dtos = new ArrayList<>();
        for (Integer id : ids) {
            Optional<Drug> drugOptional = drugRepository.getDrugById(id);
            if (drugOptional.isEmpty()) {
                throw new DrugNotFoundException();
            }
            Drug drug = drugOptional.get();
            drug.setStatus(status);
            drugRepository.save(drug);
            dtos.add(DrugMapper.toInfo(drug));
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    public ResponseEntity<List<Drug>> getAllDrugs(
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "form", required = false) List<String> forms,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "status", required = false) List<DrugStatus> status,
            @RequestParam(value = "user", required = false) String username,
            @RequestParam(value = "hasImage", required = false) Boolean imaged) {
        Pageable request = PageRequest.of(page, size);
        List<Drug> drugsList = drugRepository
                .filterDrugs(status, false, name, forms, username, imaged, request)
                .getContent();
        if (drugsList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(drugsList);
    }

    @GetMapping("/not-imaged")
    public ResponseEntity<List<Drug>> getNoImageDrugs(
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "username") String username) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Drug> drugs = drugRepository.notImagedByUserAndFilterByName(username, name.toLowerCase(), pageable);
        if (drugs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(drugs.getContent());
    }
    @GetMapping("/images")
    public ResponseEntity<List<ImageDTO>> getDrugImages(@RequestParam("drugId") Integer id) {
        Optional<Drug> d = drugRepository.getDrugById(id);
        if (d.isEmpty()) {
            throw new DrugNotFoundException();
        }
        List<ImageDTO> images = d.get().getImages().stream().map(ImageDTO::new).toList();
        if (images.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(images);
    }
}
