package com.snd.fileupload.repositories;

import com.snd.fileupload.models.Drug;
import com.snd.fileupload.models.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Integer> {
    List<Drug> findAllById(Integer id, Pageable pageable);
    Page<Drug> findAllByImagesIsEmpty(Pageable pageable);

    Drug getDrugById(Integer id);
}
