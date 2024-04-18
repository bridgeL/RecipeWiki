package anu.cookcompass.login;

public class RegisterMessage {
    public boolean successful;
    public String message;

    public RegisterMessage(boolean successful, String message){
        this.successful = successful;
        this.message = message;
    }
}
