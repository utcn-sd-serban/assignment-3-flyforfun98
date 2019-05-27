import { EventEmitter } from "events";
import RestClient from "../rest/RestClient";
import WebSocketListener from "../ws/WebSocketListener";
import answer from "./Answer";

let client = new RestClient();
let listener = new WebSocketListener();

export function getClient() {

    return client;
}


class Login extends EventEmitter {
    constructor() {
        super();
        this.state = {
            users: [],

            newUser: {
                userId: {},
                username: "",
                password: "",
                points: 0,
                userStatus: "ALLOWED",
                userPermission: "USER"
            },

            currentUser: {
                userId: {},
                username: "",
                password: "",
                points: 0,
                userStatus: "ALLOWED",
                userPermission: ""
            }
        };
    }

    loadUsers() {
        return client.getLoginClient().loadAllUsers().then(users => {
            this.state = {
                ...this.state,
                users: users
            };
            this.emit("change", this.state);
        })

    }

    addUser(username, password) {

        return client.getLoginClient().registerUser(username, password)
            .then(user => {
                this.state = {
                    ...this.state,
                    users: this.state.users.concat([user])
                };
                this.emit("change", this.state);
            });
    }

    changeNewUserProperty(property, value) {
        this.state = {
            ...this.state,
            newUser: {
                ...this.state.newUser,
                [property]: value
            }
        };
        this.emit("change", this.state);
    }

    changeLoginProperty(property, value) {
        this.state = {
            ...this.state,
            [property]: value

        };
        this.emit("change", this.state);
    }

    logUser(username, password) {

        client = new RestClient(username, password);
        listener.client.deactivate();
        listener = new WebSocketListener(username, password);
        console.log(listener.client.brokerURL);
        this.onEvent(listener);
        return client.getLoginClient().loginUser(username, password).then(user => {
            this.state = {
                ...this.state,
                currentUser: user
            };

            this.emit("change", this.state);
        })
    }

    getCurrentUser() {

        return client.getLoginClient().readCurrentUser().then(user => {
            this.state = {
                ...this.state,
                currentUser: user
            };

            this.emit("change", this.state);
        })
    }

    banUser(userId, userStatus) {

        return client.getLoginClient().banUser(userId, userStatus);
    }

    onEvent(listener){

        listener.on("event", event => {
            console.log("facem ceva?");
            if (event.type === "ANSWER_CREATED") {
                console.log("ajunge aici");
                answer.addAnswerState(event.answerDTO);
            }
        });

    }
}

const login = new Login();


export default login;