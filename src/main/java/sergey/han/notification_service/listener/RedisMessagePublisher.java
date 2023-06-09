package sergey.han.notification_service.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sergey.han.notification_service.model.NotificationModel;


@Component
@RequiredArgsConstructor
@EnableScheduling
public class RedisMessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;


    public void publish(NotificationModel message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }

    /*@Scheduled(fixedDelay = 5000)
    private void heartbeat(){
        long i=0;
        NotificationModel notificationModel=new NotificationModel().setMessage("heartbeat");
        publish(notificationModel);
    }*/
}