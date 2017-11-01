package Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;

import Services.FillService;
import SharedFiles.FillRequest;
import SharedFiles.FillResult;

/**
 * Created by devey on 2/24/17.
 */

public class FillHandler implements HttpHandler
{
    Gson gson = new Gson();
    FillService service = new FillService();
    Serializer converter = new Serializer();
    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        String user_name;
        int generations;
        System.out.println("called FillHandler");
        URI uri = httpExchange.getRequestURI(); //
        String path = uri.getPath();
        String[] seperated_path = path.split("/");
        if(seperated_path.length == 4)
        {
            user_name = seperated_path[2];
            generations = Integer.parseInt(seperated_path[3]);
        }
        else
        {
            user_name = seperated_path[2];
            generations = 4;
        }
        FillRequest frequest = new FillRequest(user_name, generations);
        FillResult fresult = service.fill(frequest);


//
//        InputStream stream = httpExchange.getRequestBody();
//        String json_request = converter.readString(stream);
//
//        FillRequest request = gson.fromJson(json_request, FillRequest.class); //the gson will create a class of the specified type.
//
////        RegisterResult result = whatever the service gives back
//        FillResult result = service.fill(request);


//        give result to decoder
//        String response = decdoed result(json string)
        String json_response = new Gson().toJson(fresult); //converts the object into json
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream os = httpExchange.getResponseBody();
        converter.writeString(json_response, os);
        os.close();
        System.out.println("finished fill service");
    }
}
