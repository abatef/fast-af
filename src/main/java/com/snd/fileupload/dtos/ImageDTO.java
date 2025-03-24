package com.snd.fileupload.dtos;

import com.snd.fileupload.models.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private Integer id;
    private String imageUrl;
    private String createdByUsername; // Instead of User object

    public ImageDTO(Image image) {
        this.id = image.getId();
        this.imageUrl = image.getUrl();
        this.createdByUsername = image.getCreatedBy() == null ? "" : image.getCreatedBy().getUsername();
    }
}