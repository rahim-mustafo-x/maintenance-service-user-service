package org.safa.maintenanceserviceuserservice.user.websocket;

import lombok.RequiredArgsConstructor;
import org.safa.maintenanceserviceuserservice.admin.interceptor.WebSocketRegisterInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
@RequiredArgsConstructor
public class ConnectionTrackerWebSocket
        implements WebSocketMessageBrokerConfigurer {

    private final WebSocketRegisterInterceptor webSocketRegisterInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Server → client destinations
        registry.enableSimpleBroker("/broadcast");

        // Client → @MessageMapping
        registry.setApplicationDestinationPrefixes("/v1/user/websocket");

        // Required for convertAndSendToUser()
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/v1/user/register-to-maintenance-service")
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(webSocketRegisterInterceptor);
    }
}