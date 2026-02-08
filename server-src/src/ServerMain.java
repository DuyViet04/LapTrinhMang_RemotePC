import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    private static int port = 5000;

    public static void main(String[] args) {
        System.out.println("Server dang chay...");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientThread(clientSocket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}