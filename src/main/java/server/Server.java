package server;

import framework.Framework;
import framework.IoC.DiscoveryUtil;
import server.model.Route;
import server.model.RouteResolver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server {

    private ServerSocket serverSocket;

    private Map<Route, RouteResolver> routes;

    public Server(String whatToServe) throws IOException {
        this.serverSocket = new ServerSocket(8080);
        Framework.getInstance().init(whatToServe);
        this.routes = DiscoveryUtil.discoverRoutes(whatToServe);
    }

    public void startListening() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            ServerThread serverThread = new ServerThread(this, socket);
            Thread thread = new Thread(serverThread);
            thread.start();
        }
    }

    public Map<Route, RouteResolver> getRoutes() {
        return routes;
    }
}
