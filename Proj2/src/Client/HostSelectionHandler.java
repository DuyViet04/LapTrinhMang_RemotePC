package Client;

public class HostSelectionHandler {
    public static String ChosenHost;

    public static void ShowHostList() {
        System.out.println("Chọn máy chủ để kết nối:");
        System.out.println("1. PC Host");
        System.out.println("2. Laptop Host");
    }

    public static void HandleHostSelection(int choice) {
        var hostList = HostMapper.GetHostList();
        if (choice > hostList.size())
            System.out.println("Không hợp lệ");
        ChosenHost = hostList.get(choice - 1);
    }
}
