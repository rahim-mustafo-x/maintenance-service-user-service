package org.safa.maintenanceserviceuserservice.imageFeignClient;

import org.safa.maintenanceserviceuserservice.ApiResponse;
import org.safa.maintenanceserviceuserservice.admin.config.ImageFeignConfig;
import org.safa.maintenanceserviceuserservice.labor.model.dto.image.ImageResponse;
import org.safa.maintenanceserviceuserservice.labor.model.model.ImageType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "image-service",
        configuration = ImageFeignConfig.class)
public interface ImageFieldClient {
    @PostMapping(value = "/v1/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ApiResponse<ImageResponse>> saveImage(@RequestPart MultipartFile file, @RequestParam long ownerId, @RequestParam ImageType imageType);
}
