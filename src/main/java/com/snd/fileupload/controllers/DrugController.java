package com.snd.fileupload.controllers;

import com.snd.fileupload.dtos.DrugCreationRequest;
import com.snd.fileupload.dtos.DrugInfoDto;
import com.snd.fileupload.exceptions.DrugNotFoundException;
import com.snd.fileupload.models.Drug;
import com.snd.fileupload.models.Image;
import com.snd.fileupload.repositories.DrugRepository;
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

    public DrugController(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<DrugInfoDto> addNewDrug(@RequestBody DrugCreationRequest request) {
        Drug drug = DrugMapper.toEntity(request);
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

    @GetMapping("/")
    public ResponseEntity<List<Drug>> getAllDrugs(@RequestParam("size") int size,
                                                  @RequestParam("page") int page) {
        Pageable request = PageRequest.of(page, size);
        Page<Drug> drugs = drugRepository.findAll(request);
        if (drugs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(drugs.getContent());
    }

    @GetMapping("/no-images")
    public ResponseEntity<List<Drug>> getNoImageDrugs(@RequestParam("size") int size,
                                                      @RequestParam("page") int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Drug> drugs = drugRepository.findAllByImagesIsEmpty(pageable);
        if (drugs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(drugs.getContent());
    }

    @GetMapping("/images")
    public ResponseEntity<List<Image>> getDrugImages(@RequestParam("drugId") Integer id) {
        Drug d = drugRepository.getDrugById(id);
        List<Image> images = d.getImages();
        if (images.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(images);
    }

}
