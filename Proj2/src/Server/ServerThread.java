package Server;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    Socket clientSocket;
    ServerGUI gui;

    public ServerThread(Socket clientSocket, ServerGUI gui) {
        this.clientSocket = clientSocket;
        this.gui = gui;
    }

    @Override
    public void run() {
        super.run();
        gui.appendLog("Tiến trình: Client đang đăng nhập từ IP " + clientSocket.getInetAddress().getHostAddress());

        try {
            ServerRequestHandler serverRequestHandler = new ServerRequestHandler();
            ServerLoginHandler serverLoginHandler = new ServerLoginHandler();

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

            // Đọc lệnh đăng nhập đầu tiên
            String loginRequest = reader.readLine();
            gui.appendLog("Yêu cầu đăng nhập từ Client: " + loginRequest);

            if (serverLoginHandler.checkLogin(loginRequest)) {
                writer.println("LOGIN_SUCCESS");
                gui.appendLog("Client (" + clientSocket.getInetAddress().getHostAddress() + ") đăng nhập thành công!");
            } else {
                writer.println("LOGIN_FAIL");
                gui.appendLog("Client đăng nhập thất bại. Đóng kết nối... (" + clientSocket.getInetAddress().getHostAddress() + ")");
                clientSocket.close();
                return; // Ngắt Thread tại đây
            }

            // Mở CMD do đăng nhập thành công
            serverRequestHandler.initCmd();

            // Thực thi lệnh đã nhận
            while (true) {
                String request = reader.readLine();
                if (request == null) {
                    gui.appendLog("Client đã ngắt kết nối. (" + clientSocket.getInetAddress().getHostAddress() + ")");
                    break;
                }
                
                gui.appendLog("Lệnh đã nhận từ Client: " + request);
                serverRequestHandler.executeCommand(request, writer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
