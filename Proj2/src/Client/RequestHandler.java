package Client;

import java.util.Scanner;

public class RequestHandler {

    public static String command;
    public static String extraCommand;

    public static void showRequest() {
        System.out.println("Chon command");
        System.out.println("1. Lay dia chi ip");
        System.out.println("2. Xem cac o dia tren may");
        System.out.println("3. Xem thong tin thu muc");
        System.out.println("4. Dieu huong sang thu muc:...");
    }

    public static void handleRequestSelection(int choice) {
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
                handleExtraCommand();
                command = "cd " + extraCommand;
                break;
            default:
                System.out.println("Khong the thuc hien lenh");
                break;
        }
    }

//    public static void handleRequestSelection(int choice, String extraCommand) {
//        switch (choice) {
//            case 4:
//                break;
//            default:
//                System.out.println("Khong the thuc hien lenh");
//                break;
//        }
//    }

    public static void handleExtraCommand() {
        System.out.println("Nhap them thong tin cau lenh: ");
        extraCommand = new Scanner(System.in).nextLine();
    }
}
