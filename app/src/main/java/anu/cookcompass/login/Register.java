package anu.cookcompass.login;

import anu.cookcompass.model.Global;
import anu.cookcompass.model.User;

public class Register {
    public RegisterMessage register(String username, String password){
        // TODO: finish this module

        // 1. check username and password format

        // 2. check if username is used

        // 3. return useful register message

        // 4. create user and update database

        User user = Global.getInstance().database.getUserByUsername(username);
        return new RegisterMessage(false, "existed username");
    }
}
