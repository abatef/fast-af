package com.snd.fileupload.utils;

import com.snd.fileupload.dtos.DrugCreationRequest;
import com.snd.fileupload.dtos.DrugInfoDto;
import com.snd.fileupload.models.Drug;

public class DrugMapper {
    public static Drug toEntity(DrugCreationRequest request) {
        Drug drug = new Drug();
        drug.setName(request.getName());
        drug.setDescription(request.getDescription());
        return drug;
    }

    public static DrugInfoDto toInfo(Drug drug) {
        DrugInfoDto dto = new DrugInfoDto();
        dto.setId(drug.getId());
        dto.setName(drug.getName());
        dto.setDescription(drug.getDescription());
        dto.setCreatedBy(drug.getCreatedBy().getUsername());
        dto.setCreateTime(drug.getCreatedAt());
        dto.setUpdateTime(drug.getUpdatedAt());
        dto.setNumberOfImages(drug.getImages().size());
        return dto;
    }
}
