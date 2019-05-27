export default class RestClientAnswer {
    constructor(authorization, BASE_URL) {
        this.authorization = authorization;
        this.BASE_URL = BASE_URL;
    }

    createAnswer(authorId, questionId, text) {

        return fetch(this.BASE_URL + "/answer", {

            method: "POST",
            body: JSON.stringify({
                authorId: authorId,
                questionId: questionId,
                text: text,
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }


    loadAllAnswers(questionId) {

        return fetch(this.BASE_URL + "/questions/" + questionId + "/answers", {

            method: "GET",
            headers: {
                "Authorization": this.authorization,
            }
        }).then(response => response.json());
    }

    updateAnswer(authorId, answerId, questionId, text) {

        return fetch(this.BASE_URL + "/answers/" + answerId, {

            method: "PUT",
            body: JSON.stringify({
                answerId: answerId,
                authorId: authorId,
                questionId: questionId,
                text: text
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    removeAnswer(authorId, answerId, questionId) {

        return fetch(this.BASE_URL + "/delete/" + answerId, {

            method: "DELETE",
            body: JSON.stringify({
                answerId: answerId,
                authorId: authorId,
                questionId: questionId
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }


    voteAnswer(userId, answerId, questionId, voteText) {

        return fetch(this.BASE_URL + "/vote-answer", {

            method: "POST",
            body: JSON.stringify({
                userGivesAnswerId: userId,
                answerVotedId: answerId,
                questionId: questionId,
                voteText: voteText
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());

    }
}