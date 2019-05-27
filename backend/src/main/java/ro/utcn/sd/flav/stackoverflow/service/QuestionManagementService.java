package ro.utcn.sd.flav.stackoverflow.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.flav.stackoverflow.dto.QuestionDTO;
import ro.utcn.sd.flav.stackoverflow.entity.*;
import ro.utcn.sd.flav.stackoverflow.exception.AccountNotFoundException;
import ro.utcn.sd.flav.stackoverflow.exception.AdminNotFoundException;
import ro.utcn.sd.flav.stackoverflow.exception.NotAVoteException;
import ro.utcn.sd.flav.stackoverflow.exception.QuestionNotFoundException;
import ro.utcn.sd.flav.stackoverflow.repository.QuestionRepository;
import ro.utcn.sd.flav.stackoverflow.repository.RepositoryFactory;

import javax.security.auth.login.AccountException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionManagementService {

    private final RepositoryFactory repositoryFactory;

    @Transactional
    public QuestionDTO addQuestion(Integer authorId, String title, String text, List<String> tags)
    {
        Date creationDate = new Date();
        Question question = new Question(authorId,title,text,new java.sql.Date(creationDate.getTime()),0);

        ArrayList<Tag> questionTags = new ArrayList<>();
        for (Tag eachTag: repositoryFactory.createTagRepository().findAll()) {
            for (String tagName: tags) {
                if(eachTag.getTitle().equals(tagName))
                    questionTags.add(eachTag);
            }
        }

        question.addTags(questionTags);
        QuestionDTO questionDTO = QuestionDTO.ofEntity(repositoryFactory.createQuestionRepository().save(question));
        ApplicationUser user = repositoryFactory.createAccountRepository().findById(questionDTO.getAuthorId()).orElseThrow(AccountNotFoundException::new);
        questionDTO.setUsername(user.getUsername());
        questionDTO.setUserPoints(user.getPoints());
        questionDTO.setUserStatus(user.getStatus().name());
        return questionDTO;
    }

    @Transactional
    public void removeQuestion(Integer userId, Integer id)
    {
        ApplicationUser user = repositoryFactory.createAccountRepository().findById(userId).orElseThrow(AccountNotFoundException::new);
        if(!user.getPermission().equals(UserPermission.ADMIN))
            throw new AdminNotFoundException();
        else {

            QuestionRepository questionRepository = repositoryFactory.createQuestionRepository();

            try {

                Question question = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
                for (Answer answer: repositoryFactory.createAnswerRepository().findAllByQuestionIdFk(question.getQuestionId())) {
                    repositoryFactory.createAnswerRepository().remove(answer);
                }

                questionRepository.remove(question);
            } catch (QuestionNotFoundException e) {
                throw new QuestionNotFoundException();
            }
        }
    }

    @Transactional
    public List<QuestionDTO> listQuestions()
    {
        List<Question> questions = repositoryFactory.createQuestionRepository().findAll();
        List<QuestionDTO> questionDTOS = questions.stream().map(QuestionDTO::ofEntity).collect(Collectors.toList());
        setAuthorOnQuestionDTOS(questionDTOS);

        return questionDTOS;
    }

    @Transactional
    private void setAuthorOnQuestionDTOS(List<QuestionDTO> questionDTOS)
    {
        questionDTOS.forEach(q -> q.setUsername(repositoryFactory.createAccountRepository()
                .findById(q.getAuthorId())
                .orElseThrow(AccountNotFoundException::new)
                .getUsername()));

        questionDTOS.forEach(q -> q.setUserPoints(repositoryFactory.createAccountRepository()
                .findById(q.getAuthorId())
                .orElseThrow(AccountNotFoundException::new)
                .getPoints()));

        questionDTOS.forEach(q -> q.setUserStatus(repositoryFactory.createAccountRepository()
                .findById(q.getAuthorId())
                .orElseThrow(AccountNotFoundException::new)
                .getStatus().name()));
    }

    @Transactional
    public void updateQuestion(int id, String text, Integer userId)
    {
        ApplicationUser user = repositoryFactory.createAccountRepository().findById(userId).orElseThrow(AccountNotFoundException::new);

        if(!user.getPermission().equals(UserPermission.ADMIN))
            throw new AdminNotFoundException();
        else {
            QuestionRepository questionRepository = repositoryFactory.createQuestionRepository();

            try {
                Question question = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
                question.setText(text);
                questionRepository.save(question);
            } catch (QuestionNotFoundException e) {
                throw new QuestionNotFoundException();
            }
        }
    }

    @Transactional
    public List<QuestionDTO> filterQuestionByTitle(String questionTitle)
    {
        List<QuestionDTO> questionDTOS = listQuestions().stream().filter(q -> q.getTitle().toLowerCase().contains(questionTitle.toLowerCase())).collect(Collectors.toList());
        setAuthorOnQuestionDTOS(questionDTOS);

        return questionDTOS;
    }

    @Transactional
    public List<QuestionDTO> filterQuestionByTag(Set<String> questionTags) {

        List<Question> questions = repositoryFactory.createQuestionRepository().findAll();
        List<Question> filteredQuestions = questions.stream().filter(q -> q.tagsToString().containsAll(questionTags)).collect(Collectors.toList());
        List<QuestionDTO> questionDTOS = filteredQuestions.stream().map(QuestionDTO::ofEntity).collect(Collectors.toList());
        setAuthorOnQuestionDTOS(questionDTOS);

        return questionDTOS;
    }

    @Transactional
    public Question getQuestionById(int questionId) {

        try {
            return repositoryFactory.createQuestionRepository().findById(questionId).orElseThrow(QuestionNotFoundException::new);
        }
        catch (QuestionNotFoundException e)
        {
            System.out.println("No question with this id was found.");
            return null;
        }
    }

    @Transactional
    public boolean handleVote(Integer userId, int questionId, String voteText) {

        boolean vote;
        if(voteText.equals("UP") || voteText.equals("DOWN")) {

            vote = voteText.equals("UP");

            VoteQuestion voteQuestion = repositoryFactory.createVoteQuestionRepository().findVoteForQuestion(userId, questionId).orElse(null);
            System.out.println(voteQuestion);
            Question question = repositoryFactory.createQuestionRepository().findById(questionId).orElse(null);

            if ((voteQuestion != null && voteQuestion.isVoteType() == vote) || question == null || userId == question.getAuthorId())
                return true;
            else {

                if (voteQuestion == null) {
                    voteQuestion = new VoteQuestion(userId, question.getAuthorId(), questionId, vote);
                    System.out.println(voteQuestion);
                }

                voteQuestion.setVoteType(vote);

                if (vote)
                    question.setScore(question.getScore() + 1);
                else
                    question.setScore(question.getScore() - 1);

                repositoryFactory.createQuestionRepository().save(question);
                repositoryFactory.createVoteQuestionRepository().save(voteQuestion);

                return false;
            }
        }
        else
            throw new NotAVoteException();
    }

    @Transactional
    public int voteCount(int questionId)
    {
        List<VoteQuestion> voteQuestions = repositoryFactory.createVoteQuestionRepository().findAllVotesOfQuestion(questionId);

        int downVotes = (int)voteQuestions.stream().filter(v -> !v.isVoteType()).count();
        int upVotes = (int)voteQuestions.stream().filter(v -> v.isVoteType()).count();

        return  upVotes - downVotes;
    }

    @Transactional
    public void updatePoints(int questionId)
    {
        List<VoteQuestion> voteQuestions = repositoryFactory.createVoteQuestionRepository().findAllVotesOfQuestion(questionId);
        Question question = repositoryFactory.createQuestionRepository().findById(questionId).orElse(null);

        int downVotes = (int)voteQuestions.stream().filter(v -> !v.isVoteType()).count();
        int upVotes = (int)voteQuestions.stream().filter(v -> v.isVoteType()).count();

        if(question != null) {

            ApplicationUser userQuestion = repositoryFactory.createAccountRepository().findById(question.getAuthorId()).orElse(null);

            if(userQuestion != null) {
                int downVotesScore = downVotes * 2;
                int upVotesScore = upVotes * 5;
                userQuestion.setPoints(userQuestion.getPoints() + upVotesScore - downVotesScore);

                repositoryFactory.createAccountRepository().save(userQuestion);
            }
        }
    }
}
