const BASE_URL = "http://localhost:8080";

export default class RestClient{
    constructor(username, password){
        this.authorization = "Basic " + btoa(username + ":" + password);
    }

    loadAllUSers(){

        return fetch(BASE_URL + "/users", {

            method: "GET",

        }).then(response => response.json());
    }

    registerUser(username, password){

        return fetch(BASE_URL + "/register", {

            method: "POST",
            body: JSON.stringify({
                username: username,
                password: password,
                userStatus: "ALLOWED",
                userPermission: "USER"
            }),
            headers:{
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    loginUser(username, password){

        return fetch(BASE_URL + "/login", {

            method: "POST",
            body: JSON.stringify({
                username: username,
                password: password,
            }),
            headers:{
           
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
          //.catch(err => err);
    }

    loadAllQuestions(){

        return fetch(BASE_URL + "/questions", {

            method: "GET",
            headers:{
                "Authorization": this.authorization
            }
        }).then(response => response.json());
    }

    loadAllTags(){

        return fetch(BASE_URL + "/tags", {

            method: "GET",
            headers:{
                "Authorization": this.authorization
            }
        }).then(response => response.json());
    }

    createTag(tags){

        return fetch(BASE_URL + "/add-tags", {

            method: "POST",
            body: JSON.stringify({
                tags: tags,
            }),
            headers:{
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    createQuestion(authorId, title, text, tags){

        return fetch(BASE_URL + "/create-question", {

            method: "POST",
            body: JSON.stringify({
                authorId: authorId,
                title: title,
                text: text,
                tags: tags
            }),
            headers:{
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    filterByTitle(title){

        return fetch(BASE_URL + "/filter-by-title", {

            method: "POST",
            body: JSON.stringify({
                title: title
            }),
            headers:{
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    filterByTags(tags){

        return fetch(BASE_URL + "/filter-by-tag", {

            method: "POST",
            body: JSON.stringify({
                tags: tags
            }),
            headers:{
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    readCurrentUser(){

        return fetch(BASE_URL + "/current-user", {

            method: "GET",
            headers:{

                "Authorization": this.authorization
            }
        }).then(response => response.json());
    }

    loadCurrentQuestion(questionId){

        return fetch(BASE_URL + "/questions/" + questionId, {

            method: "GET",
            headers:{
                "Authorization": this.authorization,
            }
        }).then(response => response.json());
    }

    createAnswer(authorId, questionId, text){

        return fetch(BASE_URL + "/create-answer", {

            method: "POST",
            body: JSON.stringify({
                authorId: authorId,
                questionId: questionId,
                text: text,
            }),
            headers:{
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }
    

    loadAllAnswers(questionId){

        return fetch(BASE_URL + "/questions/" + questionId + "/answers", {

            method: "GET",
            headers:{
                "Authorization": this.authorization,
            }
        }).then(response => response.json());
    }

    updateAnswer(authorId, answerId, questionId, text){

        return fetch(BASE_URL + "/edit-answer", {

            method: "POST",
            body: JSON.stringify({
                authorId: authorId,
                answerId: answerId,
                questionId: questionId,
                text: text
            }),
            headers:{
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    removeAnswer(authorId, answerId, questionId){

        return fetch(BASE_URL + "/remove-answer", {

            method: "POST",
            body: JSON.stringify({
                authorId: authorId,
                answerId: answerId,
                questionId: questionId
            }),
            headers:{
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    updateQuestion(userId, questionId, text){

        return fetch(BASE_URL + "/edit-question", {

            method: "POST",
            body: JSON.stringify({
                authorId: userId,
                questionId: questionId,
                text: text
            }),
            headers:{
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    removeQuestion(userId, questionId){

        return fetch(BASE_URL + "/remove-question", {

            method: "POST",
            body: JSON.stringify({
                authorId: userId,
                questionId: questionId
            }),
            headers:{
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    voteQuestion(userId, questionId, voteText){

        return fetch(BASE_URL + "/vote-question", {

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

    voteAnswer(userId, answerId, questionId, voteText){

        return fetch(BASE_URL + "/vote-answer", {

            method: "POST",
            body: JSON.stringify({
                userGivesAnswerId: userId,
                answerVotedId: answerId,
                questionId: questionId,
                voteText: voteText
            }),
            headers:{
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        }).then(response => response.json());
    }

    banUser(userId){

        return fetch(BASE_URL + "/ban-user", {

            method: "POST",
            body: JSON.stringify({
                userId: userId
            }),
            headers:{
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        })
    }

    unbanUser(userId){

        return fetch(BASE_URL + "/unban-user", {

            method: "POST",
            body: JSON.stringify({
                userId: userId
            }),
            headers:{
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        })
    }
}