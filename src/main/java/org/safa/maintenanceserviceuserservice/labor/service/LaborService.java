package org.safa.maintenanceserviceuserservice.labor.service

import org.safa.maintenanceserviceuserservice.labor.model.dto.labor.JobTypesDTO
import org.safa.maintenanceserviceuserservice.labor.model.dto.labor.LaborCreateRequest
import org.safa.maintenanceserviceuserservice.labor.model.dto.labor.LaborResponse
import org.safa.maintenanceserviceuserservice.labor.model.dto.labor.SearchLaborResponse
import org.safa.maintenanceserviceuserservice.labor.model.dto.workingHours.WorkingHourResponse
import org.safa.maintenanceserviceuserservice.labor.model.dto.workingHours.WorkingHoursUpdateRequest
import org.safa.maintenanceserviceuserservice.labor.model.model.LaborType
import org.springframework.data.domain.Page

interface LaborService {
    boolean saveWorkingHours(LaborCreateRequest laborCreateRequest, long userId)

    Page<SearchLaborResponse> searchLabors(LaborType type, int page, int size)

    LaborResponse laborById(long userId)

    Set<LaborType> jobTypes()

    Set<LaborType> meJobs(long userId)

    Set<LaborType> adjustJobType(JobTypesDTO jobTypesDTO, long userId)

    boolean deleteTypes(JobTypesDTO jobTypesDTO, long userId)

    Set<WorkingHourResponse> meWorkingHours(long userId)

    WorkingHourResponse adjustWorkingHours(WorkingHoursUpdateRequest workingHoursUpdateRequest, long userId)

    boolean deleteWorkingHours(long id, long userId)
}