package com.snd.fileupload.repositories;

import com.snd.fileupload.models.Image;
import com.snd.fileupload.models.User;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findImageByDrug_Id(Integer drugId);
    List<Image> findImagesByCreatedBy(User user);
    List<Image> findImagesByDrug_IdAndCreatedBy_Username(Integer drugId, String createdByUsername, Pageable pageable);

    boolean existsImageByCreatedBy_UsernameAndDrug_Id(@Size(max = 15) String createdByUsername, Integer drugId);
}
