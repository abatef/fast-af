package com.snd.fileupload.dtos;


import com.snd.fileupload.models.Drug;
import com.snd.fileupload.models.DrugForm;
import com.snd.fileupload.models.DrugStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DrugInfoDto {
    private Integer id;
    private String name;
    private DrugForm form;
    private Integer numberOfImages;
    private DrugStatus status;
    private String createdBy;
    private Instant createTime;
    private Instant updateTime;
}
