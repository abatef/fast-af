package com.snd.fileupload.repositories;

import com.snd.fileupload.models.Drug;
import com.snd.fileupload.models.DrugStatus;
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


    @Query("SELECT d FROM Drug d WHERE EXISTS (SELECT i FROM Image i WHERE i.createdBy.username = :username AND i.drug.id = d.id)")
    Page<Drug> findDrugsImagedByUser(@Param("username") String username, Pageable pageable);

    @Query("SELECT d FROM Drug d WHERE NOT EXISTS (SELECT i FROM Image i WHERE i.createdBy.username = :username AND i.drug.id = d.id)")
    Page<Drug> findDrugsNotImagedByUser( @Param("username") String username, Pageable pageable);

    @Query("SELECT d FROM Drug d " +
            "WHERE (:status IS NULL OR d.status = :status) " +
            "AND (:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT(:name, '%'))) " +
            "AND (:form IS NULL OR d.form = :form) " +
            "AND (:username IS NULL OR " +
            "     (:imaged IS NULL OR " +
            "      (:imaged = TRUE AND EXISTS (SELECT i FROM Image i WHERE i.createdBy.username = :username AND i.drug.id = d.id)) " +
            "   OR (:imaged = FALSE AND NOT EXISTS (SELECT i FROM Image i WHERE i.createdBy.username = :username AND i.drug.id = d.id))))")
    Page<Drug> filterDrugs(@Param("status") DrugStatus status,
                           @Param("name") String name,
                           @Param("form") String form,
                           @Param("username") String username,
                           @Param("imaged") Boolean imaged,
                           Pageable pageable);

    @Query("SELECT d FROM Drug d " +
            "WHERE (:statuses IS NULL OR " +
            "       (:useAnd = TRUE AND d.status IN :statuses) OR " +
            "       (:useAnd = FALSE AND (d.status IN :statuses OR d.status IS NULL))) " +
            "AND (:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT(:name, '%'))) " +
            "AND (:forms IS NULL OR d.form IN :forms) " +
            "AND (:imaged IS NULL OR " +
            "      (:imaged = TRUE AND EXISTS (SELECT i FROM Image i WHERE i.drug.id = d.id)) " +
            "   OR (:imaged = FALSE AND NOT EXISTS (SELECT i FROM Image i WHERE i.drug.id = d.id))) " +
            "AND (:username IS NULL OR d.createdBy.username = :username)")
    Page<Drug> filterDrugs(@Param("statuses") List<DrugStatus> statuses,
                           @Param("useAnd") boolean useAnd,
                           @Param("name") String name,
                           @Param("forms") List<String> forms,
                           @Param("username") String username,
                           @Param("imaged") Boolean imaged,
                           Pageable pageable);


    @Query("SELECT d FROM Drug d " +
            "WHERE (:statuses IS NULL OR " +
            "       (:useAnd = TRUE AND d.status IN :statuses) OR " +
            "       (:useAnd = FALSE AND (d.status IN :statuses OR d.status IS NULL))) " +
            "AND (:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT(:name, '%'))) " +
            "AND (:forms IS NULL OR d.form IN :forms) " +
            "AND (:imaged IS NULL OR " +
            "      (:imaged = TRUE AND EXISTS (SELECT i FROM Image i WHERE i.drug.id = d.id)) " +
            "   OR (:imaged = FALSE AND NOT EXISTS (SELECT i FROM Image i WHERE i.drug.id = d.id))) " +
            "AND (:username IS NULL OR d.createdBy.username = :username) " +
            "AND (:excludeUser = FALSE OR NOT EXISTS (" +
            "        SELECT i FROM Image i WHERE i.drug = d AND i.createdBy.username = :username" +
            "))")
    Page<Drug> filterDrugs(@Param("statuses") List<DrugStatus> statuses,
                           @Param("useAnd") boolean useAnd,
                           @Param("name") String name,
                           @Param("forms") List<String> forms,
                           @Param("username") String username,
                           @Param("imaged") Boolean imaged,
                           @Param("excludeUser") boolean excludeUser,
                           Pageable pageable);

}
