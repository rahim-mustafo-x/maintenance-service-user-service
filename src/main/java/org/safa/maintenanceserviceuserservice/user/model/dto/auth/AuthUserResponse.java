package org.safa.maintenanceserviceuserservice.user.model.dto.auth;

public record AuthUserResponse(
        String token,
        String refreshToken
) {}
