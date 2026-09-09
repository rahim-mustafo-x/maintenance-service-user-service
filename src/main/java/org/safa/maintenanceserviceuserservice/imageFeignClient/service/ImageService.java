package org.safa.maintenanceserviceuserservice.imageFeignClient.service;

import jakarta.servlet.http.HttpServletRequest;
import org.safa.maintenanceserviceuserservice.labor.model.dto.image.ImageResponse;
import org.safa.maintenanceserviceuserservice.labor.model.model.ImageType;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImageResponse saveImage(MultipartFile file, long ownerId, ImageType imageType);
}
