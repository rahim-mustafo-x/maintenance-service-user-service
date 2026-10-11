package org.safa.maintenanceserviceuserservice.ratings.service;

import org.safa.maintenanceserviceuserservice.ratings.model.AddRating;
import org.safa.maintenanceserviceuserservice.ratings.model.EditRating;
import org.safa.maintenanceserviceuserservice.ratings.model.Rating;
import org.springframework.data.domain.Page;

public interface RatingService {
    Rating addRate(AddRating rating);
    Rating editRate(EditRating rating);
    boolean removeRate(long rateId);
    Page<Rating> findRatesByLaborId(long laborId, int page, int size);
}
