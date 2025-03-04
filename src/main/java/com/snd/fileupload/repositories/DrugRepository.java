package com.snd.fileupload.repositories;

import com.snd.fileupload.models.Drug;
import com.snd.fileupload.models.DrugStatus;
import com.snd.fileupload.models.Image;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Integer> {
    List<Drug> findAllById(Integer id, Pageable pageable);
    Page<Drug> findAllByImagesIsEmpty(Pageable pageable);
    Optional<Drug> getDrugById(Integer id);

    Page<Drug> findAllByStatusAndDescription(DrugStatus status, String description, Pageable pageable);

    Page<Drug> findAllByStatus(@Size(max = 11) DrugStatus status, Pageable pageable);
    Page<Drug> findAllByDescription(String description, Pageable pageable);

    @Query("SELECT d FROM Drug d WHERE EXISTS (SELECT i FROM Image i WHERE i.createdBy.username = :username AND i.drug.id = d.id)")
    Page<Drug> findDrugsImagedByUser(@Param("username") String username, Pageable pageable);

    @Query("SELECT d FROM Drug d WHERE NOT EXISTS (SELECT i FROM Image i WHERE i.createdBy.username = :username AND i.drug.id = d.id)")
    Page<Drug> findDrugsNotImagedByUser( @Param("username") String username, Pageable pageable);

    @Query("SELECT d FROM Drug d " +
            "WHERE (:status IS NULL OR d.status = :status) " +
            "AND (:name IS NULL OR d.name LIKE CONCAT(:name, '%')) " +
            "AND (:description IS NULL OR d.description = :description) " +
            "AND (:username IS NULL OR :imaged IS NULL OR " +
            "      (:imaged = TRUE AND EXISTS (SELECT i FROM Image i WHERE i.createdBy.username = :username AND i.drug.id = d.id)) " +
            "   OR (:imaged = FALSE AND NOT EXISTS (SELECT i FROM Image i WHERE i.createdBy.username = :username AND i.drug.id = d.id)))")
    Page<Drug> filterDrugs(@Param("status") DrugStatus status,
                           @Param("name") String name,
                           @Param("description") String description,
                           @Param("username") String username,
                           @Param("imaged") Boolean imaged,
                           Pageable pageable);



}
