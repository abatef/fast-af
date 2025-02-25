package com.snd.fileupload.dtos;


import com.snd.fileupload.models.Drug;
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
    private String description;
    private Integer numberOfImages;
    private Instant createTime;
    private Instant updateTime;
    private String createdBy;
}
