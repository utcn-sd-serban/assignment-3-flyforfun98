package ro.utcn.sd.flav.stackoverflow.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserBannedEvent extends BaseEvent {
    private int userId;

    public UserBannedEvent(int userId) {
        super(EventType.USER_BANNED);
        this.userId = userId;
    }
}
