package org.safa.maintenanceserviceuserservice.ratings.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.safa.maintenanceserviceuserservice.labor.model.entity.LaborEntity;

import java.time.Instant;

@Entity
@Table(name = "rating")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RatingEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    //star count (3,4,5)
    private int starCount;
    //able to write thoughts abt that person
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "labor_id")
    private LaborEntity labor;
    //time when written needs for feeling of YouTube
    private Instant date;
}