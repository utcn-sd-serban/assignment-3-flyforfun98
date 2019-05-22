import loginModel from "../model/Login";

class LoadUsersCommand {

    execute() {
        return loginModel.loadUsers();
    }
}

class AddUserCommand {
    constructor(username, password) {
        this.username = username;
        this.password = password;
    }

    execute() {
        return loginModel.addUser(this.username, this.password);
    }
}

class LogUserCommand {
    constructor(username, password) {
        this.username = username;
        this.password = password;
    }

    execute() {
        return loginModel.logUser(this.username, this.password);
    }
}

class  GetCurrentUserCommand{

    execute() {
        return loginModel.getCurrentUser();
    }
}

class BanUserCommand {
    constructor(userId, userStatus) {
        this.userId = userId;
        this.userStatus = userStatus;
    }

    execute() {
        return loginModel.banUser(this.userId, this.userStatus);
    }
}

export { LoadUsersCommand, AddUserCommand, LogUserCommand, GetCurrentUserCommand, BanUserCommand };