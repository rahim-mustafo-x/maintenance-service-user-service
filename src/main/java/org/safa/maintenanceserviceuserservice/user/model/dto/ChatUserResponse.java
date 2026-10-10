package org.safa.maintenanceserviceuserservice.user.model.dto;

/** Minimal public profile returned to authenticated chat participants. */
public record ChatUserResponse(
        long id,
        String fullName,
        String phoneNumber
) {}
