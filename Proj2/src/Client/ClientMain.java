package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    public static int port = 5000;

    static void main(String[] args) {
//        SwingUtilities.invokeLater(ClientUi::new);
        Scanner scanner = new Scanner(System.in);

        HostSelectionHandler.showHostList();
        int choice = scanner.nextInt();
        HostSelectionHandler.handleHostSelection(choice);

        try {
            Socket socket = new Socket(HostSelectionHandler.ChosenHost, port);
            System.out.println("Server ip: " + HostSelectionHandler.ChosenHost);

            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                RequestHandler.showRequest();
                int rqChoice = scanner.nextInt();
                //scanner.nextLine();
                RequestHandler.handleRequestSelection(rqChoice);
                System.out.println("Lenh gui di la: " + RequestHandler.command);

                writer.println(RequestHandler.command);

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    if (line.trim().equals("Xong")) break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
