package org.safa.maintenanceserviceuserservice.ratings;

import org.safa.maintenanceserviceuserservice.ratings.model.Rating;
import org.safa.maintenanceserviceuserservice.ratings.model.entity.RatingEntity;
import org.springframework.stereotype.Component;

@Component
public class RatingMap {
    public Rating toRating(RatingEntity entity){
        return new Rating(entity.getId(), entity.getStarCount(), entity.getDescription(), entity.getLabor().getId(), entity.getDate());
    }
}
