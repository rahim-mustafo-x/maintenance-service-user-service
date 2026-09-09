package org.safa.maintenanceserviceuserservice.labor.model.dto.labor

import org.safa.maintenanceserviceuserservice.labor.model.model.LaborType

data class SearchLaborResponse(
    val id: Long,
    val laborTypes: Set<LaborType>,
    val name: String
)