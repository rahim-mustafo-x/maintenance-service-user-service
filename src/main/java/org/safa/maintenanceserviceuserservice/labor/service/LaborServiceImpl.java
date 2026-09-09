package org.safa.maintenanceserviceuserservice.labor.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.safa.maintenanceserviceuserservice.admin.exceptions.BadRequestException;
import org.safa.maintenanceserviceuserservice.admin.exceptions.NotFoundException;
import org.safa.maintenanceserviceuserservice.labor.model.dto.labor.JobTypesDTO;
import org.safa.maintenanceserviceuserservice.labor.model.dto.labor.LaborCreateRequest;
import org.safa.maintenanceserviceuserservice.labor.model.dto.labor.LaborResponse;
import org.safa.maintenanceserviceuserservice.labor.model.dto.labor.SearchLaborResponse;
import org.safa.maintenanceserviceuserservice.labor.model.dto.workingHours.WorkingHourResponse;
import org.safa.maintenanceserviceuserservice.labor.model.dto.workingHours.WorkingHoursUpdateRequest;
import org.safa.maintenanceserviceuserservice.labor.model.entity.LaborEntity;
import org.safa.maintenanceserviceuserservice.labor.model.entity.WorkingHoursEntity;
import org.safa.maintenanceserviceuserservice.labor.model.model.LaborType;
import org.safa.maintenanceserviceuserservice.labor.repository.LaborRepository;
import org.safa.maintenanceserviceuserservice.labor.repository.WorkingHoursRepository;
import org.safa.maintenanceserviceuserservice.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LaborServiceImpl implements LaborService {
    private final LaborRepository laborRepository;
    private final UserRepository userRepository;
    private final WorkingHoursRepository workingHoursRepository;
    @Override
    @Transactional
    public boolean saveWorkingHours(@NonNull LaborCreateRequest laborCreateRequest, long userId) {
        if (laborCreateRequest.workingHoursRequests().isEmpty()) {
            throw new BadRequestException("No working hours requests were provided");
        }
        var laborEntity = laborRepository.findByUserId(userId)
                .orElseGet(()->{
                    var user = userRepository.findById(userId)
                            .orElseThrow(() -> new NotFoundException("User not found"));
                    return LaborEntity.builder()
                            .laborTypes(laborCreateRequest.laborTypes())
                            .user(user)
                            .build();
                });
        Set<WorkingHoursEntity> workingHoursEntities = laborCreateRequest.workingHoursRequests().stream().map(item -> {
                    if (item.startTime().isAfter(item.endTime())) {
                        throw new BadRequestException("Start time cannot be after end time");
                    }
                    return WorkingHoursEntity.builder()
                            .labor(laborEntity)
                            .day(item.day())
                            .startTime(item.startTime())
                            .endTime(item.endTime())
                            .build();
                }
        ).collect(Collectors.toSet());
        laborEntity.getWorkingHours().clear();
        laborEntity.getWorkingHours().addAll(workingHoursEntities);
        laborRepository.save(laborEntity);
        return true;
    }

    @Override
    public LaborResponse laborById(long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        var laborEntity = laborRepository.findByUserId(userId).orElseThrow(()->new NotFoundException("User not found"));
        var workingHours = laborEntity.getWorkingHours().stream().map(
                item-> WorkingHourResponse.builder()
                        .id(item.getId())
                        .day(item.getDay())
                        .startTime(item.getStartTime())
                        .endTime(item.getEndTime())
                        .build()
        ).collect(Collectors.toSet());
        return LaborResponse.builder()
                .id(laborEntity.getId())
                .userId(laborEntity.getUser().getId())
                .fullName(user.getFullName())
                .laborTypes(laborEntity.getLaborTypes())
                .workingHours(workingHours)
                .build();
    }

    @Override
    public Set<LaborType> jobTypes() {
        return new HashSet<>(LaborType.getEntries());
    }

    @Override
    public Set<LaborType> meJobs(long userId) {
        return laborRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("User not found")).getLaborTypes();
    }

    @Override
    @Transactional
    public Set<LaborType> adjustJobType(@NonNull JobTypesDTO jobTypesDTO, long userId) {
        LaborEntity labor = laborRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("User not found"));
        labor.getLaborTypes().clear();
        labor.getLaborTypes().addAll(jobTypesDTO.laborTypes());
        laborRepository.save(labor);
        return labor.getLaborTypes();
    }

    @Override
    public Page<SearchLaborResponse> searchLabors(LaborType type, int page, int size) {
        return laborRepository.findAllByType(type, PageRequest.of(page, size)).map(item->new SearchLaborResponse(item.getId(), item.getLaborTypes(), item.getUser().getFullName()));
    }

    @Override
    @Transactional
    public boolean deleteTypes(@NonNull JobTypesDTO jobTypesDTO, long userId) {
        LaborEntity labor = laborRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("User not found"));
        labor.getLaborTypes().removeAll(jobTypesDTO.laborTypes());
        laborRepository.save(labor);
        return true;
    }

    @Override
    public Set<WorkingHourResponse> meWorkingHours(long userId) {
        return laborRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("User not found")).getWorkingHours().stream().map(item->WorkingHourResponse.builder()
                        .day(item.getDay())
                .id(item.getId())
                .startTime(item.getStartTime())
                .endTime(item.getEndTime())
                .build()).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public WorkingHourResponse adjustWorkingHours(@NonNull WorkingHoursUpdateRequest workingHoursUpdateRequest, long userId) {
        WorkingHoursEntity workingHours = workingHoursRepository.findByUserIdAndWorkId(userId, workingHoursUpdateRequest.id()).orElseThrow(()->new NotFoundException("User or working hour not found"));
        workingHours.setDay(workingHoursUpdateRequest.day());
        workingHours.setStartTime(workingHoursUpdateRequest.startTime());
        workingHours.setEndTime(workingHoursUpdateRequest.endTime());
        workingHoursRepository.save(workingHours);
        return WorkingHourResponse.builder()
                .id(workingHours.getId())
                .startTime(workingHours.getStartTime())
                .endTime(workingHours.getEndTime())
                .day(workingHours.getDay())
                .build();
    }

    @Override
    @Transactional
    public boolean deleteWorkingHours(long id, long userId) {
        workingHoursRepository.deleteByUserIdAndWork(id, userId);
        return true;
    }
}
