package ro.utcn.sd.flav.stackoverflow.dto;

import lombok.Data;
import ro.utcn.sd.flav.stackoverflow.entity.VoteQuestion;

@Data
public class VoteQuestionDTO {

    private Integer voteId;
    private Integer userGivesId;
    private Integer userGetsId;
    private Integer questionVotedId;
    private String voteText;

    public static VoteQuestionDTO ofEntity(VoteQuestion voteQuestion)
    {
        VoteQuestionDTO voteQuestionDTO = new VoteQuestionDTO();
        voteQuestionDTO.setVoteId(voteQuestion.getVoteId());
        voteQuestionDTO.setUserGetsId(voteQuestion.getUserGetsId());
        voteQuestionDTO.setUserGivesId(voteQuestion.getUserGivesId());
        voteQuestionDTO.setQuestionVotedId(voteQuestion.getQuestionVotedId());
        if(voteQuestion.isVoteType())
            voteQuestionDTO.setVoteText("UP");
        else
            voteQuestionDTO.setVoteText("DOWN");

        return voteQuestionDTO;
    }
    
}
