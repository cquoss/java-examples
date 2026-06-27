package de.quoss.socket.example;

import java.net.Socket;

public class SocketServer {

    public static void main(String... args) throws Exception {
        new SocketServer().run();
    }

    private void run() throws Exception {
        Socket socket = new Socket("localhost", 49152);
    }
    
}
