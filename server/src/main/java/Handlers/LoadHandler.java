package Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import Services.LoadService;
import SharedFiles.LoadRequest;
import SharedFiles.LoadResult;

/**
 * Created by devey on 2/24/17.
 */

public class LoadHandler implements HttpHandler{
    Gson gson = new Gson();
    LoadService service = new LoadService();
    Serializer converter = new Serializer();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
//        decoding
        InputStream stream = httpExchange.getRequestBody();
        String json_request = converter.readString(stream);
        LoadRequest request = gson.fromJson(json_request, LoadRequest.class); //the gson will create a class of the specified type.
        LoadResult result = service.load(request);

//        encoding
        String json_response = new Gson().toJson(result); //converts the object into json
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStream os = httpExchange.getResponseBody();
        converter.writeString(json_response, os);
        os.close();
    }
}
