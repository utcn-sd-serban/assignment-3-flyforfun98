package ro.utcn.sd.flav.stackoverflow.dto;

import lombok.Data;
import org.springframework.util.CollectionUtils;
import ro.utcn.sd.flav.stackoverflow.entity.Question;
import ro.utcn.sd.flav.stackoverflow.entity.Tag;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class QuestionDTO {

    private Integer questionId;
    private String username;
    private Integer authorId;
    private String title;
    private String text;
    private Date date;
    private String userStatus;
    private int votes;
    private int userPoints;
    private List<String> tags;

    public static QuestionDTO ofEntity(Question question)
    {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionId(question.getQuestionId());
        questionDTO.setAuthorId(question.getAuthorId());
        questionDTO.setTitle(question.getTitle());
        questionDTO.setText(question.getText());
        questionDTO.setDate(question.getCreationDate());
        questionDTO.setVotes(question.getScore());
        if(!CollectionUtils.isEmpty(question.getTags())){
            questionDTO.setTags(question.getTags().stream()
                .map(Tag::getTitle)
                .collect(Collectors.toList()));
        }

        return questionDTO;
    }
}
