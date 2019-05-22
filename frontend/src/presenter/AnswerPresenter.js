import answer from "../model/Answer";
import modelLogin from "../model/Login";
import modelQuestion from "../model/Question";
import invoker from "../command/Invoker";
import { AddAnswerCommand, UpdateAnswerCommand, RemoveAnswerCommand, VoteAnswerCommand, LoadAnswersCommand } from "../command/AnswerCommands";
import { VoteQuestionCommand, UpdateQuestionCommand, RemoveQuestionCommand } from "../command/QuestionCommands";
import { BanUserCommand } from "../command/LoginCommands";

class AnswerPresenter {

    onInit(questionId) {

        invoker.invoke(new LoadAnswersCommand(questionId));
    }

    onCreate() {
        var question = modelQuestion.state.currentQuestion;
        var author = modelLogin.state.currentUser;
        var text = answer.state.newAnswer.text;

        invoker.invoke(new AddAnswerCommand(author.userId, question.questionId, text)).then(() => {

            answer.changeNewAnswerProperty("text", "");
        });
    }

    onChange(property, value) {

        answer.changeNewAnswerProperty(property, value);
    }

    onChangeQuestion(property, value) {

        answer.changeCurrentQuestionProperty(property, value);
    }

    goBack() {

        window.location.assign("/#/questions/");
    }

    setAnswerModal(answerToBeEdited) {

        answer.changeAnswerProperty("answerToBeEdited", answerToBeEdited);
        answer.changeNewAnswerProperty("text", answerToBeEdited.text);
    }

    setQuestionModal(questionToBeEdited) {

        modelQuestion.changeNewQuestionProperty("text", questionToBeEdited.text);

    }

    onEdit(answerToBeEdited) {

        var text = answer.state.newAnswer.text;
        invoker.invoke(new UpdateAnswerCommand(answer.state.currentUser.userId, answerToBeEdited.answerId, answerToBeEdited.questionId, text)).then(() => {

            answer.changeNewAnswerProperty("text", "");
        });
    }

    onDelete(answerToBeDeleted) {

        invoker.invoke(new RemoveAnswerCommand(answer.state.currentUser.userId, answerToBeDeleted.answerId, answerToBeDeleted.questionId));
    }

    handleVoteAnswer(currentUser, answerToBeVoted, currentQuestion, voteText) {

        invoker.invoke(new VoteAnswerCommand(currentUser.userId, answerToBeVoted.answerId, currentQuestion.questionId, voteText));

    }

    handleVoteQuestion(currentQuestion, currentUser, voteText) {

        
        invoker.invoke(new VoteQuestionCommand(currentUser.userId, currentQuestion.questionId, voteText)).then(() => {

            answerPresenter.goBack();
        });
    }

    onEditQuestion(questionToBeEdited) {

        invoker.invoke(new UpdateQuestionCommand(answer.state.currentUser.userId, questionToBeEdited.questionId, questionToBeEdited.text));
    }

    onDeleteQuestion(questionToBeDeleted) {

        invoker.invoke(new RemoveQuestionCommand(answer.state.currentUser.userId, questionToBeDeleted.questionId)).then(() => {

            answerPresenter.goBack();
        }); 
    }

    banUser(userId, userStatus) {

        invoker.invoke(new BanUserCommand(userId, userStatus));
    }
}

const answerPresenter = new AnswerPresenter();

export default answerPresenter;