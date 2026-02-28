package Client.Services;

import Client.Models.Account;
import Client.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoginHandler {
    public static boolean IsLogin;
    private static final List<Account> accountList = new ArrayList<>();
    private static Role chosenRole;

    public static void Init() {
        if (!accountList.isEmpty()) return;
        accountList.add(new Account(Role.ADMIN, "1"));
        accountList.add(new Account(Role.USER, "1"));
    }

    public static void ShowLoginNotify() {
        System.out.println("Hãy đăng nhập để tiếp tục");
        System.out.println("1. OK");
        System.out.println("2. Bỏ");
    }

    public static void HandleLoginChoice(int choice) {
        switch (choice) {
            case 1:
                handleLoginAccount();
                break;
            case 2:
                System.out.println("Bạn chọn không đăng nhập");
                break;
            default:
                System.out.println("Lỗi");
                break;
        }
    }

    private static void handleLoginAccount() {
        System.out.println("Chọn vai trò: ");
        showRole();
        int choice = new Scanner(System.in).nextInt();
        handleRoleSelection(choice);
        System.out.println("Nhập mật khẩu: ");
        String password = new Scanner(System.in).nextLine();
        CheckLogin(chosenRole, password);
    }

    private static void showRole() {
        for (int i = 0; i < Role.values().length; i++) {
            System.out.println((i + 1) + ". " + Role.values()[i]);
        }
    }

    private static void handleRoleSelection(int choice) {
        chosenRole = Role.values()[choice - 1];
    }

    private static void CheckLogin(Role role, String password) {
        String rollPassword = accountList.get(role.ordinal()).password;
        if (role == chosenRole && password.equals(rollPassword)) {
            System.out.println("Đăng nhập thành công");
            IsLogin = true;
        } else {
            System.out.println("Đăng nhập không thành công");
            IsLogin = false;
        }
    }
}
