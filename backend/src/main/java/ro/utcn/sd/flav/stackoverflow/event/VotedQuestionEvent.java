package ro.utcn.sd.flav.stackoverflow.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utcn.sd.flav.stackoverflow.dto.VoteQuestionDTO;

@Data
@EqualsAndHashCode(callSuper = true)
public class VotedQuestionEvent extends BaseEvent {
    private final VoteQuestionDTO voteQuestionDTO;

    public VotedQuestionEvent(VoteQuestionDTO voteQuestionDTO) {
        super(EventType.VOTED_QUESTION);
        this.voteQuestionDTO = voteQuestionDTO;
    }
}
