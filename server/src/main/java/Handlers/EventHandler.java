package Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;

import Services.EventService;
import SharedFiles.AllEventResult;
import SharedFiles.EventResult;

/**
 * Created by devey on 3/10/17.
 */

public class EventHandler implements HttpHandler {
    Gson gson = new Gson();
    EventService service = new EventService();
    Serializer converter = new Serializer();
    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        String event_id;
        EventResult fresult;
        String json_response = null;
        AllEventResult apresult;
        boolean success = false;

        URI uri = httpExchange.getRequestURI(); //
        String path = uri.getPath();
        String[] seperated_path = path.split("/");
        Headers header = httpExchange.getRequestHeaders();


        if(header.containsKey("Authorization"))
        {
            String auth_token = header.getFirst("Authorization");
            if(seperated_path.length == 3)
            {
                event_id = seperated_path[2];
                fresult = service.event(event_id, auth_token);
                json_response = new Gson().toJson(fresult); //converts the object into json
                success = true;
            }
            else
            {
                apresult = service.allEvents(auth_token);
                json_response = new Gson().toJson(apresult);
                success = true;
            }
        }
        if(success) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream os = httpExchange.getResponseBody();
            converter.writeString(json_response, os);
            os.close();
        }
        else
        {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            httpExchange.getResponseBody().close();
        }
        System.out.println("finished Event service");
    }
}
