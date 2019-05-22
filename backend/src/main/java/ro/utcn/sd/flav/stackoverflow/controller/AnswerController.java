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

    @PutMapping("/answers/{answerId}")
    public List<AnswerDTO> editAnswer(@PathVariable int answerId, @RequestBody AnswerDTO answerDTO) {

        answerService.updateAnswer(answerDTO.getAuthorId(), answerId, answerDTO.getText());
        return answerService.listAnswers(answerDTO.getQuestionId());
    }

    @DeleteMapping("/delete/{answerId}")
    public List<AnswerDTO> removeAnswer(@PathVariable int answerId, @RequestBody AnswerDTO answerDTO) {

        answerService.removeAnswer(answerDTO.getAuthorId(), answerId);
        return answerService.listAnswers(answerDTO.getQuestionId());
    }

    @PostMapping("/answer")
    public AnswerDTO addAnswer(@RequestBody AnswerDTO answerDTO) {

        return answerService.addAnswer(answerDTO.getAuthorId(), answerDTO.getQuestionId(), answerDTO.getText());
    }

    @PostMapping("/vote-answer")
    public List<AnswerDTO> voteQuestion(@RequestBody VoteAnswerDTO voteAnswerDTO) {

        Integer userId = voteAnswerDTO.getUserGivesAnswerId();
        Integer answerId = voteAnswerDTO.getAnswerVotedId();
        Integer questionId = voteAnswerDTO.getQuestionId();
        String voteText = voteAnswerDTO.getVoteText();

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
