package org.safa.maintenanceserviceuserservice.user.model.dto.auth;

public record ChangePasswordRequest(
        String code,
        String newPassword
) {}