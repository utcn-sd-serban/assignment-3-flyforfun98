package ro.utcn.sd.flav.stackoverflow.dto;

import lombok.Data;
import ro.utcn.sd.flav.stackoverflow.entity.Answer;

import java.sql.Date;
import java.util.List;

@Data
public class AnswerDTO {

    private Integer answerId;
    private Integer authorId;
    private Integer questionId;
    private String text;
    private String username;
    private String userStatus;
    private Date date;
    private int votes;
    private int userPoints;

    public static AnswerDTO ofEntity(Answer answer)
    {
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setAnswerId(answer.getAnswerId());
        answerDTO.setAuthorId(answer.getAuthorIdFk());
        answerDTO.setQuestionId(answer.getQuestionIdFk());
        answerDTO.setText(answer.getText());
        answerDTO.setDate(answer.getCreationDate());
        answerDTO.setVotes(answer.getScore());

        return answerDTO;
    }
}
