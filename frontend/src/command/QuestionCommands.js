import questionModel from "../model/Question"

class LoadQuestionsCommand {

    execute() {
        return questionModel.loadQuestions();
    }
}

class LoadTagsCommand {

    execute() {
        return questionModel.loadTags();
    }
}

class AddQuestionCommand {
    constructor(authorId, title, text, tags) {
        this.authorId = authorId;
        this.title = title;
        this.text = text;
        this.tags = tags;
    }

    execute() {
        return questionModel.addQuestion(this.authorId, this.title, this.text, this.tags);
    }
}

class FilterByTitleCommand {
    constructor(searchFieldTitle){
        this.searchFieldTitle = searchFieldTitle;
    }

    execute() {
        return questionModel.filterByTitle(this.searchFieldTitle);
    }
}

class FilterByTagsCommand {
    constructor(searchFieldTag){
        this.searchFieldTag = searchFieldTag;
    }

    execute() {
        return questionModel.filterByTags(this.searchFieldTag);
    }
}

class AddNewTagsCommand {
    constructor(newTags) {
        this.newTags = newTags;
    }

    execute() {
        return questionModel.addNewTags(this.newTags);
    }
}

class LoadCurrentQuestionCommand {
    constructor(questionId){
        this.questionId = questionId;
    }

    execute() {
        return questionModel.loadCurrentQuestion(this.questionId);
    }
}

class VoteQuestionCommand {
    constructor(userId, questionId, voteText){
        this.userId = userId;
        this.questionId = questionId;
        this.voteText = voteText;
    }

    execute() {
        return questionModel.voteQuestion(this.userId, this.questionId, this.voteText);
    }  
}

class RemoveQuestionCommand {
    constructor(userId, questionId){
        this.userId = userId;
        this.questionId = questionId;
    }

    execute() {
        return questionModel.removeQuestion(this.userId, this.questionId);
    } 
}

class UpdateQuestionCommand {
    constructor(userId, questionId, text){
        this.userId = userId;
        this.questionId = questionId;
        this.text = text;
    }

    execute() {
        return questionModel.updateQuestion(this.userId, this.questionId, this.text);
    } 
}

export { LoadQuestionsCommand, LoadTagsCommand, AddQuestionCommand, LoadCurrentQuestionCommand,  
         FilterByTitleCommand,  FilterByTagsCommand, AddNewTagsCommand, 
         VoteQuestionCommand, RemoveQuestionCommand, UpdateQuestionCommand};