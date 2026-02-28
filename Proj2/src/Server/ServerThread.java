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
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

            // Mở CMD
            ServerRequestHandler.InitCmd();

            // Thực thi lệnh đã nhận
            while (true) {
                String request = reader.readLine();
                System.out.println("Lệnh đã nhận: " + request);
                ServerRequestHandler.ExecuteCommand(request, writer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
