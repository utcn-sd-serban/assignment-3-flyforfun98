package ro.utcn.sd.flav.stackoverflow.dto;

import lombok.Data;
import ro.utcn.sd.flav.stackoverflow.entity.VoteAnswer;

@Data
public class VoteAnswerDTO {

    private Integer voteId;
    private Integer userGivesAnswerId;
    private Integer userGetsAnswerId;
    private Integer answerVotedId;
    private Integer questionId;
    private String voteText;
    
    public static VoteAnswerDTO ofEntity(VoteAnswer voteAnswer)
    {
        VoteAnswerDTO voteAnswerDTO = new VoteAnswerDTO();
        voteAnswerDTO.setVoteId(voteAnswer.getVoteId());
        voteAnswerDTO.setUserGetsAnswerId(voteAnswer.getUserGetsAnswerId());
        voteAnswerDTO.setUserGivesAnswerId(voteAnswer.getUserGivesAnswerId());
        voteAnswerDTO.setAnswerVotedId(voteAnswer.getAnswerVotedId());
        if(voteAnswer.isVoteType())
            voteAnswerDTO.setVoteText("UP");
        else
            voteAnswerDTO.setVoteText("DOWN");

        return voteAnswerDTO;
    }
}
