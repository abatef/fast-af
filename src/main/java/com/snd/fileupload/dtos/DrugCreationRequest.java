package com.snd.fileupload.dtos;

import com.snd.fileupload.models.Drug;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DrugCreationRequest {
    private String name;
    private String description;
}
