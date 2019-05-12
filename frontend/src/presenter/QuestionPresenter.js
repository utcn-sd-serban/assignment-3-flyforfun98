import question from "../model/Question";
import modelLogin from "../model/Login";

class QuestionPresenter {

    onInit() {

        question.loadQuestions();
        question.loadTags();
    }

    onCreate() {

        var authorId = modelLogin.state.currentUser.userId;
        var title = question.state.newQuestion.title;
        var text = question.state.newQuestion.text;
        var tags;

        if (question.state.newQuestion.tags.length !== 0) {
            tags = question.state.newQuestion.tags.split(" ");
            question.addNewTags(tags).then(() => {

                question.addQuestion(authorId, title, text, tags).then(() => {

                    question.changeNewQuestionProperty("title", "");
                    question.changeNewQuestionProperty("text", "");
                    question.changeNewQuestionProperty("tags", "");
        
                });
            });
        }

        question.loadQuestions();
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
        
        question.loadQuestions();
    }

    onFilterByTitle() {

        var searchFieldTitle = question.state.searchFieldTitle;
        question.filterByTitle(searchFieldTitle);
        question.changeQuestionProperty("searchFieldTitle", "");

    }

    onFilterByTags() {

        var searchFieldTag = question.state.searchFieldTag;
        question.filterByTags(searchFieldTag);
        question.changeQuestionProperty("searchFieldTag", "");

    }

    onQuestionClick(index, currentQuestion) {

        question.loadCurrentQuestion(currentQuestion.questionId).then(() => {

            window.location.assign("#/questions/question/" + index);

        });
    }
}

const questionPresenter = new QuestionPresenter();

export default questionPresenter;