package anu.cookcompass.login;

import anu.cookcompass.model.Global;
import anu.cookcompass.model.User;

public class Login {
    public static Response login(String username, String password){
        // TODO: finish this module

        // 1. check username and password format

        // 2. search user and check if it is null

        // 3. check if password is right

        // 4. setup current user in global object (global is a singleton object)

        // 5. return useful login message

        User user = Global.getInstance().database.getUserByUsername(username);
        return new Response(true, "login successful");
    }

    public static Response register(String username, String password){
        // TODO: finish this module

        // 1. check username and password format

        // 2. check if username is used

        // 3. create user and update database

        // 4. return useful register message

        User user = Global.getInstance().database.getUserByUsername(username);
        return new Response(false, "existed username");
    }
}
