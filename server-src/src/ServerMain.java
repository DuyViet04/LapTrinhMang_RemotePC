import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) {
        int port = 5000;
        System.out.println("Server dang chay...");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client da ket noi.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}