package app;

import server.Server;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        Server server = new Server("app");
        server.startListening();
    }
}