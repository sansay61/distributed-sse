package sergey.han.notification_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmitterService emitterService;

    public void send(String userId, String message) {
        emitterService.getEmitter(userId).ifPresent(emitters -> {
            for (var emitter : emitters) {
                try {
                    emitter.send(message);
                } catch (Exception e) {
                    emitter.completeWithError(e);
                }
            }
        });
    }
}
