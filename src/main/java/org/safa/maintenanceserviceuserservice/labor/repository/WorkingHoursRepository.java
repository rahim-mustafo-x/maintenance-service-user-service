package org.safa.maintenanceserviceuserservice.labor.repository;

import org.safa.maintenanceserviceuserservice.labor.model.entity.WorkingHoursEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface WorkingHoursRepository extends JpaRepository<WorkingHoursEntity, Long> {

    @Query("""
            select w
            from WorkingHoursEntity w
            where w.labor.user.id = :userId
              and w.id = :wId
            """)
    Optional<WorkingHoursEntity> findByUserIdAndWorkId(
            @Param("userId") long userId,
            @Param("wId") long wId
    );

    @Transactional
    @Modifying
    @Query("""
            delete from WorkingHoursEntity w
            where w.labor.user.id = :userId
              and w.id = :wId
            """)
    void deleteByUserIdAndWork(
            @Param("wId") long wId,
            @Param("userId") long userId
    );
}