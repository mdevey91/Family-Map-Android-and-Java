package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;

/**
 * Created by devey on 2/27/17.
 */

public class DefaultHandler implements HttpHandler {
//    reads from a file and concats all the things in a file

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("called default handler");
        String seperator = File.separator;
        URI uri = exchange.getRequestURI();
        String filepath_string = uri.getPath(); //will give a string version of the URL ie "localhost8080/css/main.css
//        Headers header = exchange.getResponseHeaders();
        String[] path = filepath_string.split("/");
        String parseed_path = "";
        for(int i = 1; i < path.length;  i++)
        {
            parseed_path += seperator + path[i];
        }
        if (parseed_path.equals("")) {
            filepath_string = "HTML" + seperator + "index.html";
        } else {
            filepath_string = "HTML" + parseed_path;
        }
        try {
            File htmlFile = new File(filepath_string);
            byte[] bytes = new byte[(int)htmlFile.length()];
            FileInputStream input = new FileInputStream(htmlFile);
            input.read(bytes);
            input.close();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }catch(IOException e){
            filepath_string = "HTML" + seperator + "404.html";
            File htmlFile = new File(filepath_string);
            byte[] bytes = new byte[(int)htmlFile.length()];
            FileInputStream input = new FileInputStream(htmlFile);
            input.read(bytes);
            input.close();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }


    }
}
//String url = String.valueOf(exchange.getRequestURI()
//String filepath = "HTML" + seperator + "index.html";

//String filePathStr = filepath;
//Path filePath = FileSystems.getDefault().getPath(filePathStr);
//Files.copy(filePath, httpExch.getResponseBody());  //Takes the created filepath and sends it to the httpResponse object
//OutputStream respbody = exchange.getResponseBody();
//respBody.close();



//    private byte[] getBytesFromTextFile(File file){
//        StringBuilder result = new StringBuilder();
//        try {
//            Scanner scan = new Scanner(new BufferedReader((new FileReader(file))));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
