package org.safa.maintenanceserviceuserservice.user.controller;

import lombok.RequiredArgsConstructor;
import org.safa.maintenanceserviceuserservice.user.model.dto.websocket.PresenceRequest;
import org.safa.maintenanceserviceuserservice.user.model.dto.websocket.PresenceStatus;
import org.safa.maintenanceserviceuserservice.user.service.ConnectionMessagingService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserConnectionMessagingController {
    private final ConnectionMessagingService connectionMessagingService;
    @MessageMapping("/connection")
    //send to for public announcement while simple-messaging-template is for private person to person or server to person
    @SendTo("/broadcast/connection")
    public String connection(Principal principal, @Payload PresenceRequest presenceRequest) {
        if (principal == null) {
            return "Unauthenticated";
        }

        long userId;
        try {
            userId = Long.parseLong(principal.getName());
        } catch (NumberFormatException e) {
            return "Invalid user ID";
        }

        if (presenceRequest != null && PresenceStatus.ONLINE.equals(presenceRequest.status())) {
            connectionMessagingService.saveConnectionAndSendMessageToUser(userId, presenceRequest);
        }

        return "Presence updated for user " + userId;
    }
}
