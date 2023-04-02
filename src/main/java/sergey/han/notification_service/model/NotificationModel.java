package sergey.han.notification_service.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NotificationModel {
    private String emitterId;
    private String message;
}
