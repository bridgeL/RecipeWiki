package anu.cookcompass.login;


/**
 * @author u7760022, Xinyang Li
 * @feature Login
 * The class is a Login Response
 */
public class Response {
    public boolean successful;
    public String message;

    //Constructor of the Response object to initialize the "successful" and "message" fields of the object
    public Response(boolean successful, String message){
        this.successful = successful;
        this.message = message;
    }
}
