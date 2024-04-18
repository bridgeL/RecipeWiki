package anu.cookcompass.login;

import anu.cookcompass.model.Global;
import anu.cookcompass.model.User;

public class Login {
    public LoginMessage login(String username, String password){
        // TODO: finish this module

        // 1. check username and password format

        // 2. search user

        // 3. check if password is right

        // 4. return useful login message

        // 5. setup current user in global object (global is a singleton object)

        User user = Global.getInstance().database.getUserByUsername(username);
        return new LoginMessage(true, "login successful");
    }
}
