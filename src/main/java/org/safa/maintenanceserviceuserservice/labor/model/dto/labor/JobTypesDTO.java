package org.safa.maintenanceserviceuserservice.labor.model.dto.labor;

import org.safa.maintenanceserviceuserservice.labor.model.model.LaborType;

import java.util.Set;

public record JobTypesDTO(Set<LaborType> laborTypes) {}
