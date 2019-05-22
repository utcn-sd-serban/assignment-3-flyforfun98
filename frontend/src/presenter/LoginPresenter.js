import model from "../model/Login";
import invoker from "../command/Invoker";
import { LoadUsersCommand, AddUserCommand, LogUserCommand } from "../command/LoginCommands";

class LoginPresenter {

    onInit() {

        invoker.invoke(new LoadUsersCommand());
    }

    onCreate() {

        var username = model.state.newUser.username;
        var password = model.state.newUser.password;

        invoker.invoke(new AddUserCommand(username, password)).then(() => {

            model.changeNewUserProperty("username", "");
            model.changeNewUserProperty("password", "");
            window.location.assign("#/");
        });
       
    }

    onLogging() {

        var username = model.state.newUser.username;
        var password = model.state.newUser.password;

        invoker.invoke(new LogUserCommand(username, password)).then(() => {

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