import answer from "../model/Answer";
import modelLogin from "../model/Login";
import modelQuestion from "../model/Question";

class AnswerPresenter {

    onInit(questionId) {

        answer.loadAnswers(questionId);
    }

    onCreate() {
        var question = modelQuestion.state.currentQuestion;
        var author = modelLogin.state.currentUser;
        var text = answer.state.newAnswer.text;

        answer.addAnswer(author.userId, question.questionId, text).then(() => {

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
        answer.updateAnswer(answer.state.currentUser.userId, answerToBeEdited.answerId, answerToBeEdited.questionId, text).then(() => {

            answer.changeNewAnswerProperty("text", "");
        });
    }

    onDelete(answerToBeDeleted) {

        answer.removeAnswer(answer.state.currentUser.userId, answerToBeDeleted.answerId, answerToBeDeleted.questionId);
    }

    handleVoteAnswer(currentUser, answerToBeVoted, currentQuestion, voteText) {

        answer.voteAnswer(currentUser.userId, answerToBeVoted.answerId, currentQuestion.questionId, voteText);

    }

    handleVoteQuestion(currentQuestion, currentUser, voteText) {

        modelQuestion.voteQuestion(currentUser.userId, currentQuestion.questionId, voteText).then(() => {

            answerPresenter.goBack();
        });
    }

    onEditQuestion(questionToBeEdited) {

        modelQuestion.updateQuestion(answer.state.currentUser.userId, questionToBeEdited.questionId, questionToBeEdited.text)

    }

    onDeleteQuestion(questionToBeDeleted) {

        modelQuestion.removeQuestion(answer.state.currentUser.userId, questionToBeDeleted.questionId).then(() => {

            answerPresenter.goBack();
        }); 
    }

    banUser(userId) {

        modelLogin.banUser(userId);
    }

    unbanUser(userId) {

        modelLogin.unbanUser(userId);
    }
}

const answerPresenter = new AnswerPresenter();

export default answerPresenter;