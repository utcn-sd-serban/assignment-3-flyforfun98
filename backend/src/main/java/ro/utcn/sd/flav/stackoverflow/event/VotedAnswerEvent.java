package ro.utcn.sd.flav.stackoverflow.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utcn.sd.flav.stackoverflow.dto.VoteAnswerDTO;

@Data
@EqualsAndHashCode(callSuper = true)
public class VotedAnswerEvent extends BaseEvent {
    private final VoteAnswerDTO voteAnswerDTO;

    public VotedAnswerEvent(VoteAnswerDTO voteAnswerDTO) {
        super(EventType.VOTED_ANSWER);
        this.voteAnswerDTO = voteAnswerDTO;
    }
}
