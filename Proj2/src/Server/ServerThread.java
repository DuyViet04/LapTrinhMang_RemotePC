package Server;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    Socket clientSocket;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        super.run();
        System.out.println("Client đã kết nối, IP: " + clientSocket.getInetAddress());

        try {
            ServerRequestHandler serverRequestHandler = new ServerRequestHandler();
            ServerLoginHandler serverLoginHandler = new ServerLoginHandler();

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

            // Đọc lệnh đăng nhập đầu tiên
            String loginRequest = reader.readLine();
            System.out.println("Yêu cầu đăng nhập từ Client: " + loginRequest);

            if (serverLoginHandler.checkLogin(loginRequest)) {
                writer.println("LOGIN_SUCCESS");
                System.out.println("Client đăng nhập thành công!");
            } else {
                writer.println("LOGIN_FAIL");
                System.out.println("Client đăng nhập thất bại. Đóng kết nối...");
                clientSocket.close();
                return; // Ngắt Thread tại đây
            }

            // Mở CMD do đăng nhập thành công
            serverRequestHandler.initCmd();

            // Thực thi lệnh đã nhận
            while (true) {
                String request = reader.readLine();
                if (request == null) {
                    System.out.println("Client đã ngắt kết nối.");
                    break;
                }
                
                System.out.println("Lệnh đã nhận: " + request);
                serverRequestHandler.executeCommand(request, writer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
