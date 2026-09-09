package org.safa.maintenanceserviceuserservice.user.service;

import lombok.RequiredArgsConstructor;
import org.safa.maintenanceserviceuserservice.ApiResponse;
import org.safa.maintenanceserviceuserservice.user.model.dto.websocket.PresenceRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConnectionMessagingService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void saveConnectionAndSendMessageToUser(Long userId, PresenceRequest presenceRequest) {
        simpMessagingTemplate.convertAndSendToUser(userId.toString(),"/broadcast/connection", ApiResponse.builder().code(200).data("Good").message("Good").build());
    }
}
