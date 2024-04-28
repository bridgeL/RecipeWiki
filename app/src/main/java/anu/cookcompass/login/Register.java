package anu.cookcompass.login;


import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import anu.cookcompass.firebase.Authority;
import anu.cookcompass.model.Response;

public class Register {
    public static CompletableFuture<Response> register(String username, String password1, String password2) {
        // 1. Check username and password format

        /*
        Regex breakdown:
        a) (?=.{3}@) : Positive lookbehind assertion making sure there are at least 3 characters before @
        b) [a-zA-Z0-9]+ : One or more numbers and/or letters
        c) [a-zA-Z0-9!#$%&*-_=+/]+ : One or more numbers and/or letters and/or special characters
        d) [a-zA-Z0-9.]+ : One or more numbers and/or letters and/or periods
         */
        if (Objects.isNull(username) || username.isEmpty() || !username.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            return CompletableFuture.completedFuture(new Response(false, "Wrong username format!"));
        }

        if (Objects.isNull(password1) || password1.isEmpty()) {
            return CompletableFuture.completedFuture(new Response(false, "Empty password!"));
        }

        if (!password1.equals(password2)) {
            return CompletableFuture.completedFuture(new Response(false, "Second password doesn't match!"));
        }

        // 2. Check if username is used
        //Searches the database for the user with the given username
        return Authority.createAccount(username, password1);
    }
}
