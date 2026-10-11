package org.safa.maintenanceserviceuserservice.ratings.service;

import lombok.RequiredArgsConstructor;
import org.safa.maintenanceserviceuserservice.admin.exceptions.BadRequestException;
import org.safa.maintenanceserviceuserservice.admin.exceptions.NotFoundException;
import org.safa.maintenanceserviceuserservice.labor.model.entity.LaborEntity;
import org.safa.maintenanceserviceuserservice.labor.repository.LaborRepository;
import org.safa.maintenanceserviceuserservice.ratings.model.AddRating;
import org.safa.maintenanceserviceuserservice.ratings.model.EditRating;
import org.safa.maintenanceserviceuserservice.ratings.model.Rating;
import org.safa.maintenanceserviceuserservice.ratings.model.entity.RatingEntity;
import org.safa.maintenanceserviceuserservice.ratings.repository.RatingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final LaborRepository laborRepository;

    @Override
    @Transactional
    public Void addRate(AddRating rating) {
        if (laborRepository.existsById(rating.laborId())){
            if (starValid(rating.starCount())){
                LaborEntity laborEntity = laborRepository.findById(rating.laborId()).orElseThrow(() -> new NotFoundException("Labor not found"));
                ratingRepository.save(RatingEntity.builder()
                        .starCount(rating.starCount())
                        .description(rating.description())
                        .date(rating.date())
                        .labor(laborEntity)
                        .build());
            }else {
                throw new BadRequestException("star should be between 0..5");
            }
        }else {
            throw new NotFoundException("Labor not found");
        }
        return Void.TYPE.cast(null);
    }

    @Override
    @Transactional
    public Void editRate(EditRating rating) {
        if (ratingRepository.existsById(rating.id())) {
            if (starValid(rating.starCount())){
                LaborEntity laborEntity = laborRepository.findById(rating.laborId()).orElseThrow(() -> new NotFoundException("Labor not found"));
                ratingRepository.save(RatingEntity.builder()
                        .id(rating.id())
                        .starCount(rating.starCount())
                        .description(rating.description())
                        .date(rating.date())
                        .labor(laborEntity)
                        .build());
            }
            else{
                throw new BadRequestException("star should be between 0..5");
            }
        }else {
            throw  new NotFoundException("Rating not found");
        }
        return Void.TYPE.cast(null);
    }

    @Override
    @Transactional
    public Void removeRate(long rateId) {
        if (ratingRepository.existsById(rateId)) {
            ratingRepository.deleteById(rateId);
        } else {
            throw new NotFoundException("Not found");
        }
        return Void.TYPE.cast(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Rating> findRatesByLaborId(long laborId, int page, int size) {
        if (laborRepository.existsById(laborId)) {
            return ratingRepository.findByLaborIdPage(laborId, PageRequest.of(page, size))
                    .map(item->new Rating(item.getId(), item.getStarCount(), item.getDescription(), item.getLabor().getId(), item.getDate()));
        }else {
            throw new NotFoundException("Labor not found");
        }
    }
    private static boolean starValid(int star){
        return star>=0 && star <=5;
    }
}