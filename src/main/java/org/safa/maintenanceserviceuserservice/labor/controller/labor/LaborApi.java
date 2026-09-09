package org.safa.maintenanceserviceuserservice.labor.controller.labor;

import org.safa.maintenanceserviceuserservice.ApiResponse;
import org.safa.maintenanceserviceuserservice.labor.model.dto.image.ImageResponse;
import org.safa.maintenanceserviceuserservice.labor.model.dto.labor.JobTypesDTO;
import org.safa.maintenanceserviceuserservice.labor.model.dto.labor.LaborCreateRequest;
import org.safa.maintenanceserviceuserservice.labor.model.dto.workingHours.WorkingHourResponse;
import org.safa.maintenanceserviceuserservice.labor.model.dto.workingHours.WorkingHoursUpdateRequest;
import org.safa.maintenanceserviceuserservice.labor.model.model.ImageType;
import org.safa.maintenanceserviceuserservice.labor.model.model.LaborType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * <pre>
 *   /v1/labor
 *  │
 *  ├── POST   /                         → create labor
 *  ├── GET    /me                       → my labor
 *  ├── GET    /search                   → search labors
 *  ├── GET    /{id}                     → public labor
 *  ├── POST   /me/types                 → add type
 *  ├── DELETE /me/types/{type}          → remove type
 *  │
 *  ├── GET    /me/working-hours
 *  ├── PUT    /me/working-hours/{id}
 *  ├── DELETE /me/working-hours/{id}
 *  │
 *  └── PUT /v1/labor/me/image → to upload the image of labor for the profile
 *  │done
 *  └── PATCH  /me/availability
 * </pre>
 */

@RequestMapping("/v1/labor")
public interface LaborApi {
    //creates the profile
    @PostMapping
    ResponseEntity<ApiResponse<?>> setWorkingHours(@RequestBody LaborCreateRequest laborCreateRequest);
    @GetMapping("/me")
    ResponseEntity<ApiResponse<?>> laborMe();
    @GetMapping("/search")
    ResponseEntity<ApiResponse<?>> searchLabors(@RequestParam LaborType type, @RequestParam int page, @RequestParam int size);
    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<?>> laborById(@PathVariable long id);
    //return all job types as a list
    @GetMapping("/job-types")
    ResponseEntity<ApiResponse<Set<LaborType>>> jobTypes();
    @PutMapping("/me/job-types")
    ResponseEntity<ApiResponse<Set<LaborType>>> adjustJobType(@RequestBody JobTypesDTO jobTypesDTO);
    @GetMapping("/me/job-types")
    ResponseEntity<ApiResponse<Set<LaborType>>> meJobs();
    @DeleteMapping("/me/job-types")
    ResponseEntity<ApiResponse<Boolean>> deleteTypes(@RequestBody JobTypesDTO jobTypesDTO);
    @GetMapping("/me/working-hours")
    ResponseEntity<ApiResponse<Set<WorkingHourResponse>>> meWorkingHours();
    @PutMapping("/me/working-hours/{id}")
    ResponseEntity<ApiResponse<WorkingHourResponse>> meWorkingHoursAdjust(@RequestBody WorkingHoursUpdateRequest workingHoursUpdateRequest);
    @DeleteMapping("/me/working-hours/{id}")
    ResponseEntity<ApiResponse<Boolean>> meWorkingHoursRemove(@PathVariable long id);
    @PostMapping(value = "/me/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ApiResponse<ImageResponse>> meImage(@RequestPart MultipartFile file, @RequestParam ImageType imageType);
}
