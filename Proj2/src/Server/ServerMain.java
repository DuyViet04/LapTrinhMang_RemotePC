package Server;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    private static final int port = 5000;

    static void main(String[] args) {
        System.out.println("Server đang chạy...");
        try {
            ServerSocket socket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = socket.accept();
                new ServerThread(clientSocket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
