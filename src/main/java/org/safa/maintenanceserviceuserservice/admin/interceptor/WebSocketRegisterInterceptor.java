package org.safa.maintenanceserviceuserservice.admin.interceptor;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.safa.maintenanceserviceuserservice.user.model.dto.websocket.UserPrincipal;
import org.safa.maintenanceserviceuserservice.user.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
//channel interceptor is message based while handler interceptor is http based interceptor
public class WebSocketRegisterInterceptor implements ChannelInterceptor {
    private final JwtService jwtService;
    @Override
    public @Nullable Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        System.out.println("HIT");
        var accessor = StompHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor==null){
            return message;
        }
        if (Objects.equals(accessor.getCommand(), StompCommand.CONNECT)) {
            var authHeader = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return null;
            }
            var token = authHeader.substring(7);
            long userId = jwtService.extractUserId(token);
            accessor.setUser(new UserPrincipal(userId));
        }
        return message;
    }
}