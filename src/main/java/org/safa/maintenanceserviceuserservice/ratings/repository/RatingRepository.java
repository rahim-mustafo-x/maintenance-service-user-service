package org.safa.maintenanceserviceuserservice.ratings.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.safa.maintenanceserviceuserservice.ratings.model.entity.RatingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
    @Query("select r from RatingEntity r where r.labor.id=:laborId")
    Page<RatingEntity> findByLaborIdPage(@Param("laborId") long laborId, PageRequest pageable);

    @Query("select r from RatingEntity r where r.labor.id=:laborId")
    List<RatingEntity> findByLaborId(@Param("laborId") long laborId);
}
