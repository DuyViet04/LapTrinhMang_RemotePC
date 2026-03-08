package Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerMain {
    private static final int port = 5000;

    public static void main(String[] args) {
        System.out.println("Server đang chạy...");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Có client muốn kết nối: " + clientSocket.getInetAddress().getHostAddress());

                System.out.println("1. Chấp nhận");
                System.out.println("2. Từ chối");
                System.out.print("Chọn: ");

                int choice = 0;
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    scanner.nextLine();
                }

                switch (choice) {
                    case 1:
                        System.out.println("Đã chấp nhận kết nối. Chờ Client đăng nhập...");
                        new ServerThread(clientSocket).start();
                        break;
                    case 2:
                        System.out.println("Đã từ chối kết nối.");
                        clientSocket.close();
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ, mặc định từ chối.");
                        clientSocket.close();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
