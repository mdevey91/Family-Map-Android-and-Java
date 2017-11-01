package Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import Services.RegisterService;
import SharedFiles.RegisterRequest;
import SharedFiles.RegisterResult;

/**
 * Created by devey on 2/24/17.
 */

public class RegisterHandler implements HttpHandler{
    Gson gson = new Gson();
    RegisterService service = new RegisterService();
    Serializer converter = new Serializer();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
//        check if it's a valid method
//         the request body is what holds the info we want.

        InputStream stream = httpExchange.getRequestBody();
        String json_request = converter.readString(stream);
        RegisterRequest request = gson.fromJson(json_request, RegisterRequest.class); //the gson will create a class of the specified type.
//        RegisterResult result = whatever the service gives back
        RegisterResult result = service.register(request);


//        give result to decoder
//        String response = decdoed result(json string)
        String json_response = new Gson().toJson(result); //converts the object into json
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream os = httpExchange.getResponseBody();
        converter.writeString(json_response, os);
        os.close();
    }
}
