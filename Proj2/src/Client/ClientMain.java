package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    private static final int port = 5000;

    static void main(String[] args) {
        // Init
        HostHandler hostHandler = new HostHandler();
        LoginHandler loginHandler = new LoginHandler();
        RequestHandler requestHandler = new RequestHandler();
        Scanner scanner = new Scanner(System.in);

        // Hiển thị và xử lý danh sách host
        hostHandler.showHostList();
        int choice = scanner.nextInt();
        scanner.nextLine();
        Host chosenHost = hostHandler.handleHostSelection(choice);
        System.out.println("Bạn chọn máy chủ: " + chosenHost.hostName + ", IP: " + chosenHost.ipAddress);

        //Hiển thị thông báo đăng nhập
        loginHandler.showLoginNotify();
        int loginChoice = scanner.nextInt();
        scanner.nextLine();
        loginHandler.handleLoginChoice(loginChoice, scanner);

        if (loginHandler.isLogin()) {
            try {
                // Kết nối đến server
                Socket socket = new Socket(chosenHost.ipAddress, port);
                System.out.println("Đã kết nối đến server, IP: " + chosenHost.ipAddress);

                PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Gửi lệnh đăng nhập
                String loginRequest = loginHandler.getChosenRole().name() + " " + loginHandler.getPassword();
                writer.println(loginRequest);
                System.out.println("Lệnh đăng nhập gửi đi là: " + loginRequest);

                // Nhận phản hồi đăng nhập từ Server
                String loginResponse = reader.readLine();
                System.out.println("Kết quả đăng nhập: " + loginResponse);

                if (loginResponse != null && loginResponse.equals("LOGIN_SUCCESS")) {
                    while (true) {
                        // Hiển thị và xử lý lệnh
                        requestHandler.showRequest();
                        int rqChoice = scanner.nextInt();
                        scanner.nextLine();
                        String request = requestHandler.handleRequestSelection(rqChoice, scanner);
                        System.out.println("Lệnh gửi đi là: " + request);
                        writer.println(request);

                        // Hiển thị kết quả từ server
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            if (line.trim().equals("Xong")) break;
                        }
                    }
                } else {
                    System.out.println("Sai thông tin đăng nhập, tự động ngắt kết nối.");
                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
