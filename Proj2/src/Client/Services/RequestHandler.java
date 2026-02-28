package Client.Services;

import java.util.Scanner;

public class RequestHandler {

    public static String Command;
    private static String extraCommand;

    public static void ShowRequest() {
        System.out.println("Chọn command");
        System.out.println("1. Lấy địa chỉ IP");
        System.out.println("2. Xem các ổ đĩa trên máy");
        System.out.println("3. Xem thông tin thư mục/ổ đĩa");
        System.out.println("4. Điều hướng sang thư mục:...");
        System.out.println("5. Điều hướng sang ổ đĩa:...");
    }

    public static void HandleRequestSelection(int choice) {
        switch (choice) {
            case 1:
                Command = "ipconfig";
                break;
            case 2:
                Command = "fsutil fsinfo drives";
                break;
            case 3:
                Command = "dir";
                break;
            case 4:
                handleExtraCommand();
                Command = "cd " + extraCommand;
                break;
            case 5:
                handleExtraCommand();
                Command = "cd /d " + extraCommand + ":/";
            default:
                System.out.println("Không thể thực hiện lệnh");
                break;
        }
    }

    private static void handleExtraCommand() {
        System.out.println("Nhập ổ đĩa/tên thư mục: ");
        extraCommand = new Scanner(System.in).nextLine();
    }
}
