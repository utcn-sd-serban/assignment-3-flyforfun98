import { EventEmitter } from "events";
import { getClient } from "./Login";

class Question extends EventEmitter {
    constructor() {
        super();
        this.state = {
            questions: [],

            newQuestion: {
                username: "",
                authorId: {},
                title: "",
                text: "",
                date: new Date(),
                tags: "",
                votes: 0
            },

            currentQuestion: {
                username: "",
                authorId: {},
                title: "",
                text: "",
                date: "",
                tags: "",
                votes: ""
            },

            allTags: [],
            allTagsTemp: [],
            searchFieldTitle: "",
            searchFieldTag: ""
        };
    }

    loadQuestions() {
        return getClient().loadAllQuestions().then(questions => {
            this.state = {
                ...this.state,
                questions: questions,
            };
            this.emit("change", this.state);
        })
    }

    loadTags() {
        return getClient().loadAllTags().then(allTagsTemp => {
            this.state = {
                ...this.state,
                allTagsTemp: allTagsTemp
            };
            this.emit("change", this.state);

            for (let i = 0; i < this.state.allTagsTemp.length; i++)
                this.state.allTags[i] = this.state.allTagsTemp[i].title;
        })
    }

    addQuestion(authorId, title, text, tags) {

        return getClient().createQuestion(authorId, title, text, tags)
            .then(question => {
                this.state = {
                    ...this.state,
                    questions: this.state.questions.concat([question])
                };
                this.emit("change", this.state);
            });
    }

    changeNewQuestionProperty(property, value) {
        this.state = {
            ...this.state,
            newQuestion: {
                ...this.state.newQuestion,
                [property]: value
            }
        };
        this.emit("change", this.state);
    }

    
    changeQuestionProperty(property, value) {
        this.state = {
            ...this.state,
            [property]: value
        };
        this.emit("change", this.state);
    }


    filterByTitle(searchFieldTitle) {

        return getClient().filterByTitle(searchFieldTitle).then(questions => {
            this.state = {
                ...this.state,
                questions: questions
            };
            this.emit("change", this.state);
        })
    }

    filterByTags(searchFieldTag) {

        return getClient().filterByTags(searchFieldTag).then(questions => {
            this.state = {
                ...this.state,
                questions: questions
            };
            this.emit("change", this.state);
        })
    }

    addNewTags(newTags) {

        return getClient().createTag(newTags)
            .then(tags => {
                this.state = {
                    ...this.state,
                    allTagsTemp: tags
                };
                this.emit("change", this.state);
                for (let i = 0; i < this.state.allTagsTemp.length; i++)
                    this.state.allTags[i] = this.state.allTagsTemp[i].title;
            });
    }

    loadCurrentQuestion(questionId){

        return getClient().loadCurrentQuestion(questionId).then(currentQuestion => {
            this.state = {
                ...this.state,
                currentQuestion: currentQuestion,
            };
            this.emit("change", this.state);
        })
    }

    voteQuestion(userId, questionId, voteText){

        return getClient().voteQuestion(userId, questionId, voteText).then(questions => {
            this.state = {
                ...this.state,
                questions: questions,
            };
            this.emit("change", this.state);
        })
    }

    removeQuestion(userId, questionId){

        return getClient().removeQuestion(userId, questionId).then(questions => {
            this.state = {
                ...this.state,
                questions: questions,
            };
            this.emit("change", this.state);
        })
    }

    updateQuestion(userId, questionId, text) {

        return getClient().updateQuestion(userId, questionId, text)
            .then(questions => {

                this.state = {
                    ...this.state,
                    questions: questions
                };
                this.emit("change", this.state);
            });
    }
}

const question = new Question();

export default question;