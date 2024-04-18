package anu.cookcompass.login;

public class LoginMessage {
    public boolean successful;
    public String message;

    public LoginMessage(boolean successful, String message){
        this.successful = successful;
        this.message = message;
    }
}
