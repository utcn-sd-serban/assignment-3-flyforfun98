import { EventEmitter } from "events";
import { getClient } from "./Login";

class Answer extends EventEmitter {
    constructor() {
        super();
        this.state = {
            answers: [],
            newAnswer: {
                author: {},
                text: "",
                date: new Date(),
                question: {},
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

            currentUser: {
                userId: {},
                username: "",
                password: "",
                points: 0,
                userStatus: "ALLOWED",
                userPermission: ""
            },

            answerToBeEdited: {},
            questionText: "",
            questionToBeEdited: {}
        };
    }

    addAnswer(authorId, questionId, text) {
      
        return getClient().getAnswerClient().createAnswer(authorId, questionId, text)
            .then(answer => this.addAnswerState(answer));
    }

    addAnswerState(answer) {

        this.state = {
            ...this.state,
            answers: this.state.answers.concat([answer])
        };
        this.emit("change", this.state);
    }

    updateAnswer(authorId, answerId, questionId, text) {

        return getClient().getAnswerClient().updateAnswer(authorId, answerId, questionId, text)
            .then(answers => {

                this.state = {
                    ...this.state,
                    answers: answers
                };
                this.emit("change", this.state);
            });
    }

    removeAnswer(authorId, answerId, questionId) {

        return getClient().getAnswerClient().removeAnswer(authorId, answerId, questionId)
            .then(answers => {

                this.state = {
                    ...this.state,
                    answers: answers
                };
                this.emit("change", this.state);
            });
    }

    loadAnswers(questionId) {

        return getClient().getAnswerClient().loadAllAnswers(questionId).then(answers => {
            this.state = {
                ...this.state,
                answers: answers,
            };
            this.emit("change", this.state);
        })
    }

    loadCurrentQuestion(questionId) {

        return getClient().getQuestionClient().loadCurrentQuestion(questionId).then(currentQuestion => {
            this.state = {
                ...this.state,
                currentQuestion: currentQuestion,
            };
            this.emit("change", this.state);
        })
    }

    getCurrentUser() {

        return getClient().getLoginClient().readCurrentUser().then(user => {
            this.state = {
                ...this.state,
                currentUser: user
            };

            this.emit("change", this.state);
        })
    }


    changeNewAnswerProperty(property, value) {
        this.state = {
            ...this.state,
            newAnswer: {
                ...this.state.newAnswer,
                [property]: value
            }
        };
        this.emit("change", this.state);
    }

    changeCurrentQuestionProperty(property, value) {
        this.state = {
            ...this.state,
            currentQuestion: {
                ...this.state.currentQuestion,
                [property]: value
            }
        };
        this.emit("change", this.state);
    }

    changeAnswerProperty(property, value) {
        this.state = {
            ...this.state,
            [property]: value
        };
        this.emit("change", this.state);
    }

    voteAnswer(userId, answerId, questionId, voteText) {

        return getClient().getAnswerClient().voteAnswer(userId, answerId, questionId, voteText).then(answers => {
            this.state = {
                ...this.state,
                answers: answers,
            };
            this.emit("change", this.state);
        })
    }

    
}

const answer = new Answer();

export default answer;