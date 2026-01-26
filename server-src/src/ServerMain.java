import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    int port = 139;
    public static void main(String[] args) {
        System.out.println("Server dang chay...");
        try {
            ServerSocket serverSocket = new ServerSocket(139);
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client da ket noi.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
