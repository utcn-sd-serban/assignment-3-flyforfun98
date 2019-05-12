package ro.utcn.sd.flav.stackoverflow.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.utcn.sd.flav.stackoverflow.dto.AnswerDTO;
import ro.utcn.sd.flav.stackoverflow.dto.VoteAnswerDTO;
import ro.utcn.sd.flav.stackoverflow.service.AnswerManagementService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerManagementService answerService;

    @GetMapping("/questions/{questionId}/answers")
    public List<AnswerDTO> listAnswersForQuestion(@PathVariable int questionId) {

        return answerService.listAnswers(questionId);
    }

    @PostMapping("/edit-answer")
    public List<AnswerDTO> editAnswer(@RequestBody AnswerDTO answerDTO) {

        answerService.updateAnswer(answerDTO.getAuthorId(), answerDTO.getAnswerId(), answerDTO.getText());
        return answerService.listAnswers(answerDTO.getQuestionId());
    }

    @PostMapping("/remove-answer")
    public List<AnswerDTO> removeAnswer(@RequestBody AnswerDTO answerDTO) {

        answerService.removeAnswer(answerDTO.getAuthorId(), answerDTO.getAnswerId());
        return answerService.listAnswers(answerDTO.getQuestionId());
    }

    @PostMapping("/create-answer")
    public AnswerDTO addAnswer(@RequestBody AnswerDTO answerDTO) {

        return answerService.addAnswer(answerDTO.getAuthorId(), answerDTO.getQuestionId(), answerDTO.getText());
    }

    @PostMapping("/vote-answer")
    public List<AnswerDTO> voteQuestion(@RequestBody VoteAnswerDTO voteAnswerDTO) {


        Integer userId = voteAnswerDTO.getUserGivesAnswerId();
        Integer answerId = voteAnswerDTO.getAnswerVotedId();
        Integer questionId = voteAnswerDTO.getQuestionId();
        String voteText = voteAnswerDTO.getVoteText();

        System.out.println(userId);
        System.out.println(answerId);
        System.out.println(questionId);
        System.out.println(voteText);

        boolean isVoted = answerService.handleVote(userId,answerId,voteText);
        if(!isVoted) {

            answerService.updatePoints(answerId);
            int votes = answerService.voteCount(answerId);
            answerService.getAnswerById(answerId).setScore(votes);
            System.out.println(answerService.getAnswerById(answerId).getScore());
        }

        return answerService.listAnswers(questionId);
    }
}
