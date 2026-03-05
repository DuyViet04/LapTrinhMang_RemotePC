package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerMain {
    private static final int port = 5000;

    static void main(String[] args) {
        System.out.println("Server đang chạy...");
        try {
            ServerSocket socket = new ServerSocket(port);

            while (true) {
                Socket clientSocket = socket.accept();

                System.out.println("Có client muốn kết nối: "
                        + clientSocket.getInetAddress().getHostAddress());

                System.out.println("1. Từ chối");
                System.out.println("2. Chấp nhận");
                System.out.print("Chọn: ");

                int choice = new Scanner(System.in).nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Đã từ chối client.");
                        clientSocket.close();
                    case 2:
                        System.out.println("Đã chấp nhận client.");
                        new ServerThread(clientSocket).start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
