package org.safa.maintenanceserviceuserservice.labor.model.dto.workingHours;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record WorkingHoursUpdateRequest(
        long id,
        DayOfWeek day,
        LocalTime startTime,
        LocalTime endTime
) {}