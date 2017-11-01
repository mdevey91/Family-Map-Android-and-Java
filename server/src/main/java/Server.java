/**
 * Created by devey on 3/2/17.
 */

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import Handlers.ClearHandler;
import Handlers.DefaultHandler;
import Handlers.EventHandler;
import Handlers.FillHandler;
import Handlers.LoadHandler;
import Handlers.LoginHandler;
import Handlers.PersonHandler;
import Handlers.RegisterHandler;


public class    Server {

    private static final int MAX_WAITING_CONNECTIONS = 12;
    private static final String PORT_NUMBER = "8080";

    private HttpServer server;

    private void run() {
        System.out.println("Initializing HTTP Server");
        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(PORT_NUMBER)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null); // use the default executor

        System.out.println("Creating contexts");
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/", new DefaultHandler());

        System.out.println("Starting server");
        server.start();
    }

    public static void main(String[] args) {
        new Server().run();
    }
}

