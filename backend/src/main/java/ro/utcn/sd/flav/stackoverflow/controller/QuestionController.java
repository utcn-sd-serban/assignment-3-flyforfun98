package ro.utcn.sd.flav.stackoverflow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.utcn.sd.flav.stackoverflow.dto.QuestionDTO;
import ro.utcn.sd.flav.stackoverflow.dto.VoteQuestionDTO;
import ro.utcn.sd.flav.stackoverflow.service.QuestionManagementService;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionManagementService questionService;

    @GetMapping("/questions/{questionId}")
    public QuestionDTO currentQuestion(@PathVariable int questionId){

        for (QuestionDTO questionDTO: listAll()) {
            if(questionDTO.getQuestionId() == questionId)
                return questionDTO;
        }
        return null;
    }

    @GetMapping("/questions")
    public List<QuestionDTO> listAll() {

        return questionService.listQuestions();
    }

    @PostMapping("/create-question")
    public QuestionDTO addQuestion(@RequestBody QuestionDTO questionDTO) {

        return questionService.addQuestion(questionDTO.getAuthorId(), questionDTO.getTitle(), questionDTO.getText(),questionDTO.getTags());
    }

    @PostMapping("/remove-question")
    public List<QuestionDTO> removeQuestion(@RequestBody QuestionDTO questionDTO) {

        questionService.removeQuestion(questionDTO.getAuthorId(), questionDTO.getQuestionId());
        for (QuestionDTO question: questionService.listQuestions()) {
            System.out.println(question);

        }
        return questionService.listQuestions();
    }

    @PostMapping("/edit-question")
    public List<QuestionDTO> editQuestion(@RequestBody QuestionDTO questionDTO) {

        questionService.updateQuestion(questionDTO.getQuestionId(), questionDTO.getText(),questionDTO.getAuthorId());
        return questionService.listQuestions();
    }

    @PostMapping("/filter-by-title")
    public List<QuestionDTO> filterQuestionByTitle(@RequestBody String title) {

        int titleLength = title.length() - 2;
        String titleToBeFiltered = title.substring(10, titleLength);
        return questionService.filterQuestionByTitle(titleToBeFiltered);
    }

    @PostMapping("/filter-by-tag")
    public List<QuestionDTO> filterQuestionByTag(@RequestBody String tags) {

        int titleLength = tags.length() - 3;
        String tagsToBeFiltered = tags.substring(9, titleLength);
        String[] splitTags = tagsToBeFiltered.split(" ");
        List<String> tagsList = new ArrayList<>(Arrays.asList(splitTags));
        Set<String> tagsSet = new HashSet<>(tagsList);

        return questionService.filterQuestionByTag(tagsSet);
    }

    @PostMapping("/vote-question")
    public List<QuestionDTO> voteQuestion(@RequestBody VoteQuestionDTO voteQuestionDTO) {

        Integer userId = voteQuestionDTO.getUserGivesId();
        Integer questionId = voteQuestionDTO.getQuestionVotedId();
        String voteText = voteQuestionDTO.getVoteText();

        boolean isVoted = questionService.handleVote(userId,questionId,voteText);
        if(!isVoted) {

            questionService.updatePoints(questionId);
            int votes = questionService.voteCount(questionId);
            questionService.getQuestionById(questionId).setScore(votes);
            System.out.println(questionService.getQuestionById(questionId).getScore());
        }

        return questionService.listQuestions();
    }
}
