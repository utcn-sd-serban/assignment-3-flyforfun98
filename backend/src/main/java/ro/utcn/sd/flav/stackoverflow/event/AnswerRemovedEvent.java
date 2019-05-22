package ro.utcn.sd.flav.stackoverflow.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerRemovedEvent extends BaseEvent {
    private int answerId;

    public AnswerRemovedEvent(int answerId) {
        super(EventType.ANSWER_REMOVED);
        this.answerId = answerId;
    }
}
