package Client.Controllers;

import Client.Services.HostHandler;
import Client.Services.LoginHandler;
import Client.Services.RequestHandler;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    private static final int port = 5000;

    static void main(String[] args) {
//        SwingUtilities.invokeLater(ClientUi::new);
        // Init
        HostHandler.Init();
        LoginHandler.Init();

        Scanner scanner = new Scanner(System.in);

        // Hiển thị và xử lý danh sách host
        HostHandler.ShowHostList();
        int choice = scanner.nextInt();
        HostHandler.HandleHostSelection(choice);

        //Hiển thị thông báo đăng nhập
        LoginHandler.ShowLoginNotify();
        int loginChoice = scanner.nextInt();
        LoginHandler.HandleLoginChoice(loginChoice);

        if (LoginHandler.IsLogin) {
            try {
                // Kết nối đến server
                Socket socket = new Socket(HostHandler.ChosenHost.ipAddress, port);
                System.out.println("Server ip: " + HostHandler.ChosenHost);

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
}
