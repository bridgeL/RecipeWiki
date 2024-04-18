package anu.cookcompass.login;

public class Response {
    public boolean successful;
    public String message;

    public Response(boolean successful, String message){
        this.successful = successful;
        this.message = message;
    }
}
