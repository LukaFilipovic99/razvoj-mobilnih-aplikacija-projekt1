package filipovic.youtube_api_demo_app.exception;

public class ApiCallFailedException extends Exception{

    public ApiCallFailedException (String apiUrl){
        super("Failed to call api: " + apiUrl);
    }
}
