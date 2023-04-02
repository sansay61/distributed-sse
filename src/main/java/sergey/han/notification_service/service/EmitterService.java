package sergey.han.notification_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class EmitterService {
    private final ConcurrentHashMap<String, Set<SseEmitter>> emitterConcurrentHashMap = new ConcurrentHashMap<>();

    public SseEmitter createEmitter(String userId) {
        SseEmitter sseEmitter = new SseEmitter();

        sseEmitter.onCompletion(() -> {
            sseEmitter.complete();
            log.info("emitter {} completed", userId);
            this.detachEmitter(userId, sseEmitter);
        });
        sseEmitter.onTimeout(() -> {
            sseEmitter.complete();
            log.info("emitter {} timed out", userId);
            this.detachEmitter(userId, sseEmitter);
        });
        sseEmitter.onError((e) -> {
            sseEmitter.complete();
            log.info("emitter {} fall in error state", userId);
            this.detachEmitter(userId, sseEmitter);
        });
        Set<SseEmitter> sseEmitters = this.emitterConcurrentHashMap.computeIfAbsent(userId, (c) -> ConcurrentHashMap.newKeySet());
        sseEmitters.add(sseEmitter);
        return sseEmitter;
    }

    public Optional<Set<SseEmitter>> getEmitter(String userId) {
        return Optional.ofNullable(emitterConcurrentHashMap.get(userId));
    }

    private void detachEmitter(String userId, SseEmitter emitter){
        Set<SseEmitter> emitters = this.emitterConcurrentHashMap.get(userId);
        emitters.remove(emitter);
        if (emitters.isEmpty())
            this.emitterConcurrentHashMap.remove(userId);
    }
}
