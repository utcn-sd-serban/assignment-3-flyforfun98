package ro.utcn.sd.flav.stackoverflow.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utcn.sd.flav.stackoverflow.dto.QuestionDTO;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionUpdatedEvent extends BaseEvent {
    private final QuestionDTO questionDTO;

    public QuestionUpdatedEvent(QuestionDTO questionDTO) {
        super(EventType.QUESTION_UPDATED);
        this.questionDTO = questionDTO;
    }
}
