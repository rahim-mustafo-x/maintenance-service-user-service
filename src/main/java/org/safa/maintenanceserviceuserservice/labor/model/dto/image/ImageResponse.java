package org.safa.maintenanceserviceuserservice.labor.model.dto.image;

import org.safa.maintenanceserviceuserservice.labor.model.model.ImageType;

import java.util.UUID;

public record ImageResponse(
        UUID id,
        String contentType,
        String fileName,
        long ownerId,
        ImageType imageType
) {}