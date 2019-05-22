import answerModel from "../model/Answer";

class AddAnswerCommand {
    constructor(authorId, questionId, text) {
        this.authorId = authorId;
        this.questionId = questionId;
        this.text = text;
    }

    execute() {
        return answerModel.addAnswer(this.authorId, this.questionId, this.text);
    }
}

class UpdateAnswerCommand {
    constructor(authorId, answerId, questionId, text){
        this.authorId = authorId;
        this.answerId = answerId;
        this.questionId = questionId;
        this.text = text;
    }

    execute(){
        return answerModel.updateAnswer(this.authorId, this.answerId, this.questionId, this.text);
    }
}

class RemoveAnswerCommand {
    constructor(authorId, answerId, questionId){
        this.authorId = authorId;
        this.answerId = answerId;
        this.questionId = questionId;
    }

    execute(){
        return answerModel.removeAnswer(this.authorId, this.answerId, this.questionId);
    }
}

class LoadAnswersCommand {
    constructor(questionId){
        this.questionId = questionId;
    }

    execute(){
        return answerModel.loadAnswers(this.questionId);
    }
}

class LoadCurrentQuestionCommand {
    constructor(questionId){
        this.questionId = questionId;
    }

    execute() {
        return answerModel.loadCurrentQuestion(this.questionId);
    }
}

class GetCurrentUserCommand {
    
    execute(){
        return answerModel.getCurrentUser();
    }
}

class VoteAnswerCommand {
    constructor(userId, answerId, questionId, voteText){
        this.userId = userId;
        this.answerId = answerId;
        this.questionId = questionId;
        this.voteText = voteText;
    }

    execute(){
        return answerModel.voteAnswer(this.userId, this.answerId, this.questionId, this.voteText);
    }
}


export { AddAnswerCommand, UpdateAnswerCommand, RemoveAnswerCommand, LoadAnswersCommand,
         LoadCurrentQuestionCommand, GetCurrentUserCommand, VoteAnswerCommand};