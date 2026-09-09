package org.safa.maintenanceserviceuserservice.user.model.dto;

import java.util.Set;

public record MaintenanceUser(
        String userName,
        Set<String> roles,
        String password
) {}
