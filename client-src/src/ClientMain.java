import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    public static int port = 5000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            // Chon host server
            HostSelectionHandler.showHostServerOptions();
            int choice = scanner.nextInt();
            HostSelectionHandler.handleHostSelection(choice);
            System.out.println("Da ket noi den may chu: " + HostSelectionHandler.chosenHost);

            // Ket noi den server
            Socket socket = new Socket(HostSelectionHandler.chosenHost, port);

            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                // Hien thi va chon request
                RequestHandler.showRequestOptions();
                choice = scanner.nextInt();
                RequestHandler.handleRequest(choice);
                System.out.println("Yeu cau da duoc gui den server: " + RequestHandler.executionResult);
                writer.println(RequestHandler.executionResult);

                String line;
                while (!(line = reader.readLine()).equals("Xong")) {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}