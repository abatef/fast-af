package com.snd.fileupload.utils;

import com.snd.fileupload.dtos.DrugCreationRequest;
import com.snd.fileupload.dtos.DrugInfoDto;
import com.snd.fileupload.models.Drug;

public class DrugMapper {
    public static Drug toEntity(DrugCreationRequest request) {
        Drug drug = new Drug();
        drug.setName(request.getName());
        drug.setForm(request.getForm());
        return drug;
    }

    public static DrugInfoDto toInfo(Drug drug) {
        DrugInfoDto dto = new DrugInfoDto();
        dto.setId(drug.getId());
        dto.setName(drug.getName());
        dto.setForm(drug.getForm());
        dto.setCreateTime(drug.getCreatedAt());
        dto.setUpdateTime(drug.getUpdatedAt());
        dto.setStatus(drug.getStatus());
        dto.setCreatedBy(drug.getCreatedBy() != null ? drug.getCreatedBy().getUsername() : null);
        dto.setNumberOfImages(drug.getImages() != null ? drug.getImages().size() : 0);
        return dto;
    }
}
