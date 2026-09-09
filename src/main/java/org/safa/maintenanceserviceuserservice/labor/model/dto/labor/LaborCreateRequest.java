package org.safa.maintenanceserviceuserservice.labor.model.dto.labor;

import org.safa.maintenanceserviceuserservice.labor.model.dto.workingHours.WorkingHoursCreateRequest;
import org.safa.maintenanceserviceuserservice.labor.model.model.LaborType;
import java.util.Set;

public record LaborCreateRequest(
        Set<WorkingHoursCreateRequest> workingHoursRequests,
        Set<LaborType> laborTypes
) {}
