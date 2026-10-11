package org.safa.maintenanceserviceuserservice.ratings.model;

import java.time.Instant;

public record AddRating(
        int starCount,
        String description,
        long laborId,
        Instant date
) {
}
