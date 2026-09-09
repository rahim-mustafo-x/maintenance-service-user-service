package org.safa.maintenanceserviceuserservice.imageFeignClient.service;

import lombok.RequiredArgsConstructor;
import org.safa.maintenanceserviceuserservice.imageFeignClient.ImageFieldClient;
import org.safa.maintenanceserviceuserservice.labor.model.dto.image.ImageResponse;
import org.safa.maintenanceserviceuserservice.labor.model.model.ImageType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements  ImageService {
    private final ImageFieldClient imageFieldClient;

    @Override
    public ImageResponse saveImage(MultipartFile file, long ownerId, ImageType imageType) {
        return Objects.requireNonNull(imageFieldClient.saveImage(file, ownerId, imageType).getBody()).getData();
    }

}
