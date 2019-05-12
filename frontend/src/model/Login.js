import { EventEmitter } from "events";
import RestClient from "../rest/RestClient";
//import WebSocketListener from "../ws/WebSocketListener";

let client = new RestClient();
//const listener = new WebSocketListener("flavius1", "parola1");

export function getClient(){

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
        return client.loadAllUSers().then(users => {
            this.state = {
                ...this.state,
                users: users
            };
            this.emit("change", this.state);
        })

    }

    addUser(username, password) {

        return client.registerUser(username, password)
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
        return client.loginUser(username, password).then(user => {
            this.state = {
                ...this.state,
                currentUser: user
            };
           
            this.emit("change", this.state);
        })
    }

    getCurrentUser(){

        return client.readCurrentUser().then(user => {
            this.state = {
                ...this.state,
                currentUser: user
            };
           
            this.emit("change", this.state);
        }) 
    }

    banUser(userId) {

        return client.banUser(userId);
    }

    unbanUser(userId) {

        return client.unbanUser(userId);
    }
}

const login = new Login();

export default login;