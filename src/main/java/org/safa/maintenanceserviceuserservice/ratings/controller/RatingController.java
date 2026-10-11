package org.safa.maintenanceserviceuserservice.ratings.controller;

import lombok.RequiredArgsConstructor;
import org.safa.maintenanceserviceuserservice.ApiResponse;
import org.safa.maintenanceserviceuserservice.admin.exceptions.BadRequestException;
import org.safa.maintenanceserviceuserservice.admin.exceptions.NotFoundException;
import org.safa.maintenanceserviceuserservice.ratings.model.AddRating;
import org.safa.maintenanceserviceuserservice.ratings.model.EditRating;
import org.safa.maintenanceserviceuserservice.ratings.model.Rating;
import org.safa.maintenanceserviceuserservice.ratings.service.RatingService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RatingController implements RatingApi {
    private final RatingService ratingService;
    @Override
    public ResponseEntity<ApiResponse<Rating>> addRate(AddRating rating) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Rating>builder()
                            .code(HttpStatus.OK.value())
                            .data(ratingService.addRate(rating))
                            .build());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Rating>builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Rating>builder()
                            .code(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Rating>> editRate(EditRating rating) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Rating>builder()
                            .code(HttpStatus.OK.value())
                            .data(ratingService.editRate(rating))
                            .build());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Rating>builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Rating>builder()
                            .code(HttpStatus.BAD_REQUEST.value())
                            .message(e.getMessage())
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Boolean>> removeRate(long rateId) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Boolean>builder()
                            .code(HttpStatus.OK.value())
                            .data(ratingService.removeRate(rateId))
                            .build());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Boolean>builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build());
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Page<Rating>>> findRatesByLaborId(long laborId, int page, int size) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Page<Rating>>builder()
                            .code(HttpStatus.OK.value())
                            .data(ratingService.findRatesByLaborId(laborId, page, size))
                            .build());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.<Page<Rating>>builder()
                            .code(HttpStatus.NOT_FOUND.value())
                            .message(e.getMessage())
                            .build());
        }
    }
}
