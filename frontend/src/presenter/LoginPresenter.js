import model from "../model/Login";
class LoginPresenter {

    onInit() {

        model.loadUsers();
    }

    onCreate() {

        var username = model.state.newUser.username;
        var password = model.state.newUser.password;

        model.addUser(username, password).then(() => {

            model.changeNewUserProperty("username", "");
            model.changeNewUserProperty("password", "");
            window.location.assign("#/");
        });
    }

    onLogging() {

        var username = model.state.newUser.username;
        var password = model.state.newUser.password;

        model.logUser(username, password).then(() => {

            model.changeNewUserProperty("username", "");
            model.changeNewUserProperty("password", "");
            
            window.location.assign("#/questions");
        });
    }

    onChange(property, value) {
        
        model.changeNewUserProperty(property, value);
    }

}

const loginPresenter = new LoginPresenter();

export default loginPresenter;