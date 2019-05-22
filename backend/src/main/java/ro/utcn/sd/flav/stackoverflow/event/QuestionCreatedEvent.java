package ro.utcn.sd.flav.stackoverflow.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utcn.sd.flav.stackoverflow.dto.QuestionDTO;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionCreatedEvent extends BaseEvent {
    private final QuestionDTO questionDTO;

    public QuestionCreatedEvent(QuestionDTO questionDTO) {
        super(EventType.QUESTION_CREATED);
        this.questionDTO = questionDTO;
    }
}
