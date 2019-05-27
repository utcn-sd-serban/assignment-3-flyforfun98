export default class RestClientQuestion {
    constructor(authorization, BASE_URL) {
        this.authorization = authorization;
        this.BASE_URL = BASE_URL;
    }

    loadAllQuestions() {

        return fetch(this.BASE_URL + "/questions", {

            method: "GET",
            headers: {
                "Authorization": this.authorization
            }
        }).then(response => response.json());
    }

    loadAllTags() {

        return fetch(this.BASE_URL + "/tags", {

            method: "GET",
            headers: {
                "Authorization": this.authorization
            }
        }).then(response => response.json());
    }

    createTag(tags) {

        return fetch(this.BASE_URL + "/tag", {

            method: "POST",
            body: JSON.stringify({
                tags: tags,
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    createQuestion(authorId, title, text, tags) {

        return fetch(this.BASE_URL + "/question", {

            method: "POST",
            body: JSON.stringify({
                authorId: authorId,
                title: title,
                text: text,
                tags: tags
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    filterByTitle(title) {

        return fetch(this.BASE_URL + "/filter-by-title", {

            method: "POST",
            body: JSON.stringify({
                title: title
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    filterByTags(tags) {

        return fetch(this.BASE_URL + "/filter-by-tag", {

            method: "POST",
            body: JSON.stringify({
                tags: tags
            }),
            headers: {
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    updateQuestion(userId, questionId, text){

        return fetch(this.BASE_URL + "/questions/" + questionId, {

            method: "PUT",
            body: JSON.stringify({
                questionId: questionId,
                authorId: userId,
                text: text
            }),
            headers:{
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    removeQuestion(userId, questionId){

        return fetch(this.BASE_URL + "/questions/" + questionId, {

            method: "DELETE",
            body: JSON.stringify({
                questionId: questionId,
                authorId: userId
            }),
            headers:{
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    voteQuestion(userId, questionId, voteText){

        return fetch(this.BASE_URL + "/vote-question", {

            method: "POST",
            body: JSON.stringify({
                userGivesId: userId,
                questionVotedId: questionId,
                voteText: voteText
            }),
            headers:{
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    loadCurrentQuestion(questionId){

        return fetch(this.BASE_URL + "/questions/" + questionId, {

            method: "GET",
            headers:{
                "Authorization": this.authorization,
            }
        }).then(response => response.json());
    }
}