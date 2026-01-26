import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) {
        String pcHost = "10.10.239.176";
        int port = 5000;

        try {
            Socket socket = new Socket(pcHost, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
