package sergey.han.notification_service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import sergey.han.notification_service.model.NotificationModel;
import sergey.han.notification_service.service.NotificationService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisMessageSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;


    public void onMessage(final Message message, final byte[] pattern) {
        try {
            var notificationModel= objectMapper.readValue(message.toString(), NotificationModel.class);
            notificationService.send(notificationModel.getEmitterId(),notificationModel.getMessage());
        } catch (IOException e) {
            log.error("Exception occurred while receiving message from notification topic", e);
        }
        String channelName = new String(message.getChannel(), StandardCharsets.UTF_8);
        log.info("Channel {}: message received", channelName);
    }
}