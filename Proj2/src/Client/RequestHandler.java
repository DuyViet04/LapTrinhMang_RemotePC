package Client;

import java.util.Scanner;

public class RequestHandler {
    // Hiển thị câu lệnh
    public void showRequest() {
        System.out.println("Chọn command: ");
        System.out.println("1. Lấy địa chỉ IP");
        System.out.println("2. Xem các ổ đĩa trên máy");
        System.out.println("3. Xem thông tin thư mục/ổ đĩa");
        System.out.println("4. Điều hướng sang thư mục:...");
        System.out.println("5. Điều hướng sang ổ đĩa:...");
    }

    // Xử lý câu lệnh
    public String handleRequestSelection(int choice, Scanner scanner) {
        String extraCommand = "";
        String command = "";
        switch (choice) {
            case 1:
                command = "ipconfig";
                break;
            case 2:
                command = "fsutil fsinfo drives";
                break;
            case 3:
                command = "dir";
                break;
            case 4:
                extraCommand = handleExtraCommand(scanner);
                command = "cd " + extraCommand;
                break;
            case 5:
                extraCommand = handleExtraCommand(scanner);
                command = "cd /d " + extraCommand + ":/";
                break;
            default:
                System.out.println("Không có câu lệnh");
                break;
        }
        return command;
    }

    // Xử lý câu lệnh mở rộng
    private String handleExtraCommand(Scanner scanner) {
        System.out.println("Nhập ổ đĩa/tên thư mục: ");
        return scanner.nextLine();
    }
}
