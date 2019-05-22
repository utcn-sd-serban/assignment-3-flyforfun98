export default class RestClientLogin {
    constructor(authorization, BASE_URL) {
        this.authorization = authorization;
        this.BASE_URL = BASE_URL;
    }

    loadAllUsers(){

        return fetch(this.BASE_URL + "/list-users", {

            method: "GET",

        }).then(response => response.json());
    }

    registerUser(username, password){

        return fetch(this.BASE_URL + "/users", {

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

        return fetch(this.BASE_URL + "/login", {

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

    readCurrentUser(){

        return fetch(this.BASE_URL + "/current-user", {

            method: "GET",
            headers:{

                "Authorization": this.authorization
            }
        }).then(response => response.json());
    }

    banUser(userId, userStatus){

        return fetch(this.BASE_URL + "/user/" + userId, {

            method: "PUT",
            body: JSON.stringify({

                status: userStatus
            }),
            headers:{
                "Authorization": this.authorization,
                "Content-Type": "application/json"
            }
        })
    }
}