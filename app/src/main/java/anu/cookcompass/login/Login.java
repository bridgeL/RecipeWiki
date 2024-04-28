package anu.cookcompass.login;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import anu.cookcompass.firebase.Authority;
import anu.cookcompass.model.Response;


public class Login {
    public static CompletableFuture<Response> login(String username, String password) {
        // 1. Check username and password format

        /*
        Regex breakdown:
        a) [A-Za-z0-9._%+-]+ : One or more numbers and/or letters and/or special characters
        b) @ : @ symbol in emails
        c) [A-Za-z0-9.-]+ : One or more numbers, letters, dots, and/or hyphens
        d) \\. : A dot
        e) [A-Za-z]{2,} : Two or more letters
         */
        if (Objects.isNull(username) || username.isEmpty() || !username.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            return CompletableFuture.completedFuture(new Response(false, "Wrong username format!"));
        }

        if (Objects.isNull(password) || password.isEmpty()) {
            return CompletableFuture.completedFuture(new Response(false, "Empty password!"));
        }

        // 2. Search user and check if it is null
        //Searches the database for the user with the given username
        return Authority.signIn(username, password);
    }
}
