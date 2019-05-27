import question from "../model/Question";
import modelLogin from "../model/Login";
import invoker from "../command/Invoker";
import { LoadQuestionsCommand, LoadTagsCommand, AddQuestionCommand, LoadCurrentQuestionCommand,  
        FilterByTitleCommand,  FilterByTagsCommand, AddNewTagsCommand } from "../command/QuestionCommands";

class QuestionPresenter {

    onInit() {

        invoker.invoke(new LoadQuestionsCommand());
        invoker.invoke(new LoadTagsCommand());
    
    }

    onCreate() {

        var authorId = modelLogin.state.currentUser.userId;
        var title = question.state.newQuestion.title;
        var text = question.state.newQuestion.text;
        var tags;

        if (question.state.newQuestion.tags.length !== 0) {
            tags = question.state.newQuestion.tags.split(" ");
            invoker.invoke(new AddNewTagsCommand(tags)).then(() => {

                invoker.invoke(new AddQuestionCommand(authorId, title, text, tags)).then(() => {

                    question.changeNewQuestionProperty("title", "");
                    question.changeNewQuestionProperty("text", "");
                    question.changeNewQuestionProperty("tags", "");
        
                });
            });
        }

        invoker.invoke(new LoadQuestionsCommand());
    }

    onChangeQuestion(property, value) {

        question.changeQuestionProperty(property, value);
    }

    onChange(property, value) {

        question.changeNewQuestionProperty(property, value);
    }

    logOut() {

        modelLogin.changeLoginProperty("currentUser", "");
        window.location.assign("/#/");
    }

    showQuestions() {
        
        invoker.invoke(new LoadQuestionsCommand());
    }

    onFilterByTitle() {

        var searchFieldTitle = question.state.searchFieldTitle;
        invoker.invoke(new FilterByTitleCommand(searchFieldTitle));
        question.changeQuestionProperty("searchFieldTitle", "");

    }

    onFilterByTags() {

        var searchFieldTag = question.state.searchFieldTag;
        invoker.invoke(new FilterByTagsCommand(searchFieldTag));
        question.changeQuestionProperty("searchFieldTag", "");

    }

    onQuestionClick(index, currentQuestion) {

        invoker.invoke(new LoadCurrentQuestionCommand(currentQuestion.questionId)).then(() => {

            window.location.assign("#/questions/question/" + index);

        });
    }
}

const questionPresenter = new QuestionPresenter();

export default questionPresenter;