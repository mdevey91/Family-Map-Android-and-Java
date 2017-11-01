package Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import Services.ClearService;
import SharedFiles.ClearResult;

/**
 * Created by devey on 2/24/17.
 */

public class ClearHandler implements HttpHandler{
    Gson gson = new Gson();
    ClearService service = new ClearService();
    Serializer converter = new Serializer();
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
//        check if it's a valid method
//         the request body is what holds the info we want.

        System.out.println("Called clear handler!");
//        RegisterResult result = whatever the service gives back
        ClearResult result = service.clear();


//        give result to decoder
//        String response = decdoed result(json string)
        String json_response = new Gson().toJson(result); //converts the object into json
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream os = httpExchange.getResponseBody();
        converter.writeString(json_response, os);
        os.close();
    }
}
