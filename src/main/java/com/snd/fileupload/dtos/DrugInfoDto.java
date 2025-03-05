package com.snd.fileupload.dtos;


import com.snd.fileupload.models.DrugStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DrugInfoDto {
    private Integer id;
    private String name;
    private String form;
    private Integer numberOfImages;
    private DrugStatus status;
    private String createdBy;
    private Instant createTime;
    private Instant updateTime;
}
