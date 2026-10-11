package org.safa.maintenanceserviceuserservice.ratings.model;

import java.time.Instant;

public record Rating(
        long id,
        int starCount,
        String description,
        long laborId,
        Instant date
) {}
