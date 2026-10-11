package org.safa.maintenanceserviceuserservice.ratings.controller;

import org.safa.maintenanceserviceuserservice.ApiResponse;
import org.safa.maintenanceserviceuserservice.ratings.model.AddRating;
import org.safa.maintenanceserviceuserservice.ratings.model.EditRating;
import org.safa.maintenanceserviceuserservice.ratings.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("v1/ratings")
public interface RatingApi {
    @PostMapping
    ResponseEntity<ApiResponse<Void>> addRate(@RequestBody AddRating rating);
    @PutMapping
    ResponseEntity<ApiResponse<Void>> editRate(@RequestBody EditRating rating);
    @DeleteMapping
    ResponseEntity<ApiResponse<Void>> removeRate(@RequestParam long rateId);
    @GetMapping
    ResponseEntity<ApiResponse<Page<Rating>>> findRatesByLaborId(@RequestParam long laborId, @RequestParam int page, @RequestParam int size);
}