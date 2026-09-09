package org.safa.maintenanceserviceuserservice.labor.controller.labor;

import lombok.RequiredArgsConstructor;
import org.safa.maintenanceserviceuserservice.ApiResponse;
import org.safa.maintenanceserviceuserservice.imageFeignClient.service.ImageService;
import org.safa.maintenanceserviceuserservice.labor.model.dto.image.ImageResponse;
import org.safa.maintenanceserviceuserservice.labor.model.dto.labor.JobTypesDTO;
import org.safa.maintenanceserviceuserservice.labor.model.dto.labor.LaborCreateRequest;
import org.safa.maintenanceserviceuserservice.admin.exceptions.BadRequestException;
import org.safa.maintenanceserviceuserservice.admin.exceptions.NotFoundException;
import org.safa.maintenanceserviceuserservice.labor.model.dto.labor.LaborResponse;
import org.safa.maintenanceserviceuserservice.labor.model.dto.labor.SearchLaborResponse;
import org.safa.maintenanceserviceuserservice.labor.model.dto.workingHours.WorkingHourResponse;
import org.safa.maintenanceserviceuserservice.labor.model.dto.workingHours.WorkingHoursUpdateRequest;
import org.safa.maintenanceserviceuserservice.labor.model.model.ImageType;
import org.safa.maintenanceserviceuserservice.labor.model.model.LaborType;
import org.safa.maintenanceserviceuserservice.labor.service.LaborService;
import org.safa.maintenanceserviceuserservice.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.Objects;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class LaborController implements LaborApi {
    private final LaborService laborService;
    private final UserService userService;
    private final ImageService imageService;
    private long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = Objects.requireNonNull(authentication).getName();
        return userService.findUserIdByUserName(username);
    }

    @Override
    public ResponseEntity<ApiResponse<?>>  setWorkingHours(LaborCreateRequest laborCreateRequest){
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.builder()
                            .code(HttpStatus.ACCEPTED.value())
                            .data(laborService.saveWorkingHours(laborCreateRequest, getCurrentUserId()))
                            .build());
        }catch (NullPointerException | BadRequestException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.builder()
                            .code(HttpStatus.NOT_ACCEPTABLE.value())
                            .message(e.getMessage())
                            .build());
        } catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> laborMe() {
        try {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.builder()
                            .code(HttpStatus.FOUND.value())
                            .data(laborService.laborById(getCurrentUserId()))
                            .build());
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> searchLabors(LaborType type, int page, int size) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Page<SearchLaborResponse>>builder()
                            .code(HttpStatus.FOUND.value())
                            .data(laborService.searchLabors(type, page, size))
                            .build());
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<?>> laborById(long id) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<LaborResponse>builder()
                            .code(HttpStatus.FOUND.value())
                            .data(laborService.laborById(id))
                            .build());
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Set<LaborType>>> adjustJobType(JobTypesDTO jobTypesDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Set<LaborType>>builder()
                            .code(HttpStatus.OK.value())
                            .data(laborService.adjustJobType(jobTypesDTO, getCurrentUserId()))
                            .build());
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Set<LaborType>>builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Set<LaborType>>> jobTypes() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Set<LaborType>>builder()
                            .code(HttpStatus.OK.value())
                            .data(laborService.jobTypes())
                            .build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Set<LaborType>>builder()
                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message(e.getMessage())
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Set<LaborType>>> meJobs() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Set<LaborType>>builder()
                            .code(HttpStatus.OK.value())
                            .data(laborService.meJobs(getCurrentUserId()))
                            .build());
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Set<LaborType>>builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> deleteTypes(JobTypesDTO jobTypesDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Boolean>builder()
                            .code(HttpStatus.OK.value())
                            .data(laborService.deleteTypes(jobTypesDTO, getCurrentUserId()))
                            .build());
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Boolean>builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Set<WorkingHourResponse>>> meWorkingHours() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Set<WorkingHourResponse>>builder()
                            .code(HttpStatus.OK.value())
                            .data(laborService.meWorkingHours(getCurrentUserId()))
                            .build());
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Set<WorkingHourResponse>>builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<WorkingHourResponse>> meWorkingHoursAdjust(WorkingHoursUpdateRequest workingHoursUpdateRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<WorkingHourResponse>builder()
                            .code(HttpStatus.OK.value())
                            .data(laborService.adjustWorkingHours(workingHoursUpdateRequest, getCurrentUserId()))
                            .build());
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<WorkingHourResponse>builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> meWorkingHoursRemove(long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Boolean>builder()
                            .code(HttpStatus.OK.value())
                            .data(laborService.deleteWorkingHours(id, getCurrentUserId()))
                            .build());
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Boolean>builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<ImageResponse>> meImage(MultipartFile file, ImageType imageType) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<ImageResponse>builder()
                            .code(HttpStatus.CREATED.value())
                            .data(imageService.saveImage(file, getCurrentUserId(), imageType))
                            .message(null)
                            .build());
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<ImageResponse>builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .data(null)
                            .message(e.getMessage())
                            .build());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<ImageResponse>builder()
                            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .data(null)
                            .message(e.getMessage())
                            .build());
        }
    }
}