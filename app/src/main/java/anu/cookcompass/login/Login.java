package anu.cookcompass.login;

import anu.cookcompass.database.LocalDatabase;
import anu.cookcompass.model.Global;
import anu.cookcompass.model.User;

public class Login {
    public static Response login(String username, String password) {
        // 1. Check username and password format

        /*
        Regex breakdown:
        a) (?=.{3}@) : Positive lookbehind assertion making sure there are at least 3 characters before @
        b) [a-zA-Z0-9]+ : One or more numbers and/or letters
        c) [a-zA-Z0-9!#$%&*-_=+/]+ : One or more numbers and/or letters and/or special characters
        d) [a-zA-Z0-9.]+ : One or more numbers and/or letters and/or periods
         */

        if (username.isEmpty() || username == null || password.isEmpty() || password == null ||
                username.contains(" ") || password.contains(" ") ||
                !username.matches("^(?=.{3}@)[a-zA-Z0-9]+[a-zA-Z0-9!#$%&*-_=+/]+@[a-zA-Z0-9.]+$")
                || username.contains("..")) {
            return new Response(false, "Wrong username or password format!");
        }

        // 2. Search user and check if it is null

        //Searches the database for the user with the given username
        User user = Global.getInstance().database.getUserByUsername(username);

        //Check if the user is null or not (Check if the user exists in the database)
        if (user == null) {
            return new Response(false, "Username does not exist!");
        }

        // 3. Check if the password is correct
        if (user != null && !password.equals(user.password)) {
            return new Response(false, "Wrong password!");
        }

        /*
        4. Assigns the user "user" to the currentUser member variable of the global instance (the Singleton object)
        of the Global class. In other words: Set "user" as the value of the global instance's currentUser variable.
         */
        Global.getInstance().currentUser = user;

        // 5. Return successful login message
        return new Response(true, "Login successful!");
    }

    public static Response register(String username, String password) {
        // 1. Check username and password format

        /*
        Regex breakdown:
        a) (?=.{3}@) : Positive lookbehind assertion making sure there are at least 3 characters before @
        b) [a-zA-Z0-9]+ : One or more numbers and/or letters
        c) [a-zA-Z0-9!#$%&*-_=+/]+ : One or more numbers and/or letters and/or special characters
        d) [a-zA-Z0-9.]+ : One or more numbers and/or letters and/or periods
         */

        if (username.isEmpty() || username == null || password.isEmpty() || password == null ||
                username.contains(" ") || password.contains(" ") ||
                !username.matches("^(?=.{3}@)[a-zA-Z0-9]+[a-zA-Z0-9!#$%&*-_=+/]+@[a-zA-Z0-9.]+$")
                || username.contains("..")) {
            return new Response(false, "Wrong username or password format!");
        }

        // 2. Check if username is used

        //Searches the database for the user with the given username
        User user = Global.getInstance().database.getUserByUsername(username);

        //Check if the user is null or not (Check if the user exists in the database)
        if (user != null) {
            return new Response(false, "Username already exists!");
        }

        // 3. Create user and update database
        if (user == null) {
            user = new User();
            Global.getInstance().database.addUser(user);
        }

        // 4. Return successful register message
        return new Response(true, "Registration successful!");
    }
}
