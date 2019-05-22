import RestClientLogin from "./RestClientLogin";
import RestClientQuestion from "./RestClientQuestion";
import RestClientAnswer from "./RestClientAnswer";

const BASE_URL = "http://localhost:8080";

export default class RestClient{
    constructor(username, password){
        this.authorization = "Basic " + btoa(username + ":" + password);
        this.loginClient = new RestClientLogin(this.authorization, BASE_URL);
        this.questionClient = new RestClientQuestion(this.authorization, BASE_URL);
        this.answerClient = new RestClientAnswer(this.authorization, BASE_URL);
        
    }

    getLoginClient() {
        return this.loginClient;
    }

    getQuestionClient() {
        return this.questionClient;
    }

    getAnswerClient() {
        return this.answerClient;
    }
    
}