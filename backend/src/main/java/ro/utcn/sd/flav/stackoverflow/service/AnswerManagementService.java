package ro.utcn.sd.flav.stackoverflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.flav.stackoverflow.dto.AnswerDTO;
import ro.utcn.sd.flav.stackoverflow.entity.Answer;
import ro.utcn.sd.flav.stackoverflow.entity.ApplicationUser;
import ro.utcn.sd.flav.stackoverflow.entity.UserPermission;
import ro.utcn.sd.flav.stackoverflow.entity.VoteAnswer;
import ro.utcn.sd.flav.stackoverflow.event.AnswerCreatedEvent;
import ro.utcn.sd.flav.stackoverflow.exception.AccountNotFoundException;
import ro.utcn.sd.flav.stackoverflow.exception.AnswerNotFoundException;
import ro.utcn.sd.flav.stackoverflow.exception.AnswerRemovalException;
import ro.utcn.sd.flav.stackoverflow.exception.NotAVoteException;
import ro.utcn.sd.flav.stackoverflow.repository.AnswerRepository;
import ro.utcn.sd.flav.stackoverflow.repository.RepositoryFactory;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerManagementService {

    private final RepositoryFactory repositoryFactory;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public AnswerDTO addAnswer(Integer authorId, Integer questionId, String text)
    {
        java.util.Date date = new java.util.Date();
        Answer answer = new Answer(authorId, questionId, text, new Date(date.getTime()), 0);

        AnswerDTO answerDTO = AnswerDTO.ofEntity(repositoryFactory.createAnswerRepository().save(answer));
        ApplicationUser user = repositoryFactory.createAccountRepository().findById(answerDTO.getAuthorId()).orElseThrow(AccountNotFoundException::new);
        answerDTO.setUsername(user.getUsername());
        answerDTO.setUserPoints(user.getPoints());
        answerDTO.setUserStatus(user.getStatus().name());

        eventPublisher.publishEvent(new AnswerCreatedEvent(answerDTO));

        return answerDTO;
    }

    @Transactional
    public void removeAnswer(Integer userId, Integer answerId)
    {
        AnswerRepository answerRepository = repositoryFactory.createAnswerRepository();

        try {

            ApplicationUser user = repositoryFactory.createAccountRepository().findById(userId).orElseThrow(AccountNotFoundException::new);
            Answer answer = answerRepository.findById(answerId).orElseThrow(AnswerNotFoundException::new);

            if (!answer.getAuthorIdFk().equals(user.getUserId()) && !user.getPermission().equals(UserPermission.ADMIN)) {
                throw new AnswerRemovalException();
            }
            else
                answerRepository.remove(answer);
        }
        catch (AnswerNotFoundException e)
        {
            throw new AnswerNotFoundException();
        }
    }

    @Transactional
    public List<AnswerDTO> listAnswers()
    {
        List<Answer> answers = repositoryFactory.createAnswerRepository().findAll();
        List<AnswerDTO> answerDTOS = answers.stream().map(AnswerDTO::ofEntity).collect(Collectors.toList());
        setAuthorOnAnswerDTOS(answerDTOS);
        return answerDTOS;

    }

    @Transactional
    public void updateAnswer(Integer userId, Integer answerId, String text)
    {
        AnswerRepository answerRepository = repositoryFactory.createAnswerRepository();

        try {

            ApplicationUser user = repositoryFactory.createAccountRepository().findById(userId).orElseThrow(AccountNotFoundException::new);
            Answer answer = answerRepository.findById(answerId).orElseThrow(AnswerNotFoundException::new);

            if (!answer.getAuthorIdFk().equals(user.getUserId()) && !user.getPermission().equals(UserPermission.ADMIN)) {
                System.out.println("No permission to edit the answer");
            } else {
                answer.setText(text);
                answerRepository.save(answer);
            }
        }
        catch (AnswerNotFoundException e)
        {
            System.out.println("No answer id or user was found");
        }
    }

    @Transactional
    public Answer getAnswerById(int answerId) {

        try {
            return repositoryFactory.createAnswerRepository().findById(answerId).orElseThrow(AnswerNotFoundException::new);
        }
        catch (AnswerNotFoundException e)
        {
            System.out.println("No answer with this id was found.");
            return null;
        }
    }

    @Transactional
    public List<AnswerDTO> listAnswers (int questionIdFk) {

        List<Answer> answers = repositoryFactory.createAnswerRepository().findAllByQuestionIdFk(questionIdFk).stream()
                .sorted(Comparator.comparing(Answer::getScore).reversed()).collect(Collectors.toList());
        List<AnswerDTO> answerDTOS = answers.stream().map(AnswerDTO::ofEntity).collect(Collectors.toList());
        setAuthorOnAnswerDTOS(answerDTOS);
        return answerDTOS;
    }

    @Transactional
    private void setAuthorOnAnswerDTOS(List<AnswerDTO> answerDTOS)
    {
        answerDTOS.forEach(q -> q.setUsername(repositoryFactory.createAccountRepository()
                .findById(q.getAuthorId())
                .orElseThrow(AccountNotFoundException::new)
                .getUsername()));

        answerDTOS.forEach(q -> q.setUserPoints(repositoryFactory.createAccountRepository()
                .findById(q.getAuthorId())
                .orElseThrow(AccountNotFoundException::new)
                .getPoints()));

        answerDTOS.forEach(q -> q.setUserStatus(repositoryFactory.createAccountRepository()
                .findById(q.getAuthorId())
                .orElseThrow(AccountNotFoundException::new)
                .getStatus().name()));
    }

    @Transactional
    public boolean handleVote(Integer userId, int answerId, String voteText) {


        boolean vote;
        if(voteText.equals("UP") || voteText.equals("DOWN")) {
            vote = voteText.equals("UP");

            VoteAnswer voteAnswer = repositoryFactory.createVoteAnswerRepository().findVoteForAnswer(userId, answerId).orElse(null);
            Answer answer = repositoryFactory.createAnswerRepository().findById(answerId).orElse(null);
            ApplicationUser userVote = repositoryFactory.createAccountRepository().findById(userId).orElse(null);

            if ((voteAnswer != null && voteAnswer.isVoteType() == vote) || answer == null || userId == answer.getAuthorIdFk())
                return true;
            else {
                if (voteAnswer == null)
                    voteAnswer = new VoteAnswer(userId, answer.getAuthorIdFk(), answerId, vote);

                voteAnswer.setVoteType(vote);

                if (vote) {
                    answer.setScore(answer.getScore() + 1);
                } else {
                    answer.setScore(answer.getScore() - 1);
                    if (userVote != null) {

                        userVote.setPoints(userVote.getPoints() - 1);
                        repositoryFactory.createAccountRepository().save(userVote);
                    }
                }
                repositoryFactory.createAnswerRepository().save(answer);
                repositoryFactory.createVoteAnswerRepository().save(voteAnswer);

                return false;
            }
        }
        else
            throw new NotAVoteException();
    }

    @Transactional
    public int voteCount(int answerId)
    {
        List<VoteAnswer> voteAnswer = repositoryFactory.createVoteAnswerRepository().findAllVotesOfAnswer(answerId);

        int downVotes = (int)voteAnswer.stream().filter(v -> !v.isVoteType()).count();
        int upVotes = (int)voteAnswer.stream().filter(v -> v.isVoteType()).count();

        return  upVotes - downVotes;
    }


    @Transactional
    public void updatePoints(int answerId)
    {
        List<VoteAnswer> voteAnswer = repositoryFactory.createVoteAnswerRepository().findAllVotesOfAnswer(answerId);
        Answer answer = repositoryFactory.createAnswerRepository().findById(answerId).orElse(null);

        int downVotes = (int)voteAnswer.stream().filter(v -> !v.isVoteType()).count();
        int upVotes = (int)voteAnswer.stream().filter(v -> v.isVoteType()).count();

        if(answer != null) {

            ApplicationUser userAnswer = repositoryFactory.createAccountRepository().findById(answer.getAuthorIdFk()).orElse(null);

            if(userAnswer != null) {

                int downVotesScore = downVotes * 2;
                int upVotesScore = upVotes * 10;
                userAnswer.setPoints(userAnswer.getPoints() + upVotesScore - downVotesScore);

                repositoryFactory.createAccountRepository().save(userAnswer);
            }
        }
    }
}
