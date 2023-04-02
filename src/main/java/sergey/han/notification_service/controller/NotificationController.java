package sergey.han.notification_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sergey.han.notification_service.service.EmitterService;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final EmitterService emitterService;

    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        var userId = (String)((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication()).getTokenAttributes().get("sub");
        return emitterService.createEmitter(userId);
    }
}
