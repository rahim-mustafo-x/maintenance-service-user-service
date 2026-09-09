package org.safa.maintenanceserviceuserservice.user.model.dto.auth.login;

import org.safa.maintenanceserviceuserservice.user.model.UserRole;

public record LoginUserRequest(
        String username,
        String password,
        UserRole role
){}