package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    private static final int port = 5000;

    static void main(String[] args) {
//        SwingUtilities.invokeLater(ClientUi::new);
        Scanner scanner = new Scanner(System.in);

        // Hiển thị và xử lý danh sách host
        HostSelectionHandler.ShowHostList();
        int choice = scanner.nextInt();
        HostSelectionHandler.HandleHostSelection(choice);

        try {
            // Kết nối đến server
            Socket socket = new Socket(HostSelectionHandler.ChosenHost, port);
            System.out.println("Server ip: " + HostSelectionHandler.ChosenHost);

            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                // Hiển thị và xử lý lệnh
                RequestHandler.ShowRequest();
                int rqChoice = scanner.nextInt();
                RequestHandler.HandleRequestSelection(rqChoice);
                System.out.println("Lệnh gửi đi là: " + RequestHandler.Command);
                writer.println(RequestHandler.Command);

                // Hiển thị kết quả từ server
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    if (line.trim().equals("Xong")) break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
