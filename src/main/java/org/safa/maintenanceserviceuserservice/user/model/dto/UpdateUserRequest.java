package org.safa.maintenanceserviceuserservice.user.model.dto;

public record UpdateUserRequest(
        String fullName,
        String username,
        String phoneNumber
) {}
