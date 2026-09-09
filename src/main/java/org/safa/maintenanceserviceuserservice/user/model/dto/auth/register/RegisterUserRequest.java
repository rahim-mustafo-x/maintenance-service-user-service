package org.safa.maintenanceserviceuserservice.user.model.dto.auth.register;

import org.safa.maintenanceserviceuserservice.user.model.UserRole;

public record RegisterUserRequest(
        String fullName,
        String username,
        String phoneNumber,
        String password,
        UserRole role
) {
}
