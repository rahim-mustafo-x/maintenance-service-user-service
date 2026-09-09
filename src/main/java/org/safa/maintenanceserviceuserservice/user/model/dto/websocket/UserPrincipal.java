package org.safa.maintenanceserviceuserservice.user.model.dto.websocket;

import java.security.Principal;

public record UserPrincipal(long userId) implements Principal {

    @Override
    public String getName() {
        return String.valueOf(userId);
    }
}
