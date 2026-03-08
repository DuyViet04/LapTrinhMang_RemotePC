package Client;

import java.util.Scanner;

public class LoginHandler {
    private boolean isLogin;
    private Role chosenRole;
    private String password;

    public boolean isLogin() {
        return isLogin;
    }

    public Role getChosenRole() {
        return chosenRole;
    }

    public String getPassword() {
        return password;
    }

    // Hiển thị lựa chọn đăng nhập
    public void showLoginNotify() {
        System.out.println("Hãy đăng nhập để tiếp tục");
        System.out.println("1. OK");
        System.out.println("2. Bỏ");
    }

    // Xử lý lựa chọn đăng nhập
    public void handleLoginChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                handleLoginAccount(scanner);
                break;
            case 2:
                System.out.println("Bạn chọn không đăng nhập. Tự động thoát.");
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ");
                break;
        }
    }

    // Xử lý tài khoản đăng nhập
    private void handleLoginAccount(Scanner scanner) {
        // Hiển thị vai trò
        System.out.println("Chọn vai trò để đăng nhập: ");
        showRole();

        // Xử lý lựa chọn
        int choice = scanner.nextInt();
        scanner.nextLine();
        chosenRole = handleRoleSelection(choice);

        // Nhập mật khẩu
        System.out.println("Nhập mật khẩu: ");
        password = scanner.nextLine();

        isLogin = true;
    }

    // Hiển thị vai trò
    private static void showRole() {
        for (int i = 0; i < Role.values().length; i++) {
            System.out.println((i + 1) + ". " + Role.values()[i]);
        }
    }

    // Xử lý lựa chọn vai trò
    private Role handleRoleSelection(int choice) {
        int roleRange = Role.values().length;
        if (choice <= 0 || choice > roleRange) {
            return null;
        }
        return Role.values()[choice - 1];
    }
}
