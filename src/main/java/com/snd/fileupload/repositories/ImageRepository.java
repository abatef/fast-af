package com.snd.fileupload.repositories;

import com.snd.fileupload.models.Image;
import com.snd.fileupload.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findImageByDrug_Id(Integer drugId);
    List<Image> findImagesByCreatedBy(User user);
}
