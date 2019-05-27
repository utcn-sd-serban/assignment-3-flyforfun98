package ro.utcn.sd.flav.stackoverflow.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionRemovedEvent extends BaseEvent {
    private int questionId;

    public QuestionRemovedEvent(int questionId) {
        super(EventType.QUESTION_REMOVED);
        this.questionId = questionId;
    }
}
