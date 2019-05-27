package ro.utcn.sd.flav.stackoverflow.event;

import lombok.Data;

@Data
public class BaseEvent {
    private final EventType type;
}