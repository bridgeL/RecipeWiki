package anu.cookcompass.model;

public class Response {
    public boolean successful;
    public String message;

    //Constructor of the Response object to initialize the "successful" and "message" fields of the object
    public Response(boolean successful, String message){
        this.successful = successful;
        this.message = message;
    }
}
