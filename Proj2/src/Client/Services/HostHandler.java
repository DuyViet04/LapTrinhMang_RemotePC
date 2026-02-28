package Client.Services;

import Client.Models.Host;

import java.util.ArrayList;
import java.util.List;

public class HostHandler {
    private static final String vyesPcHost = "10.10.239.176";
    private static final String vyesLaptopHost = "10.10.239.119";
    private static final String vtLaptopHost = "10.10.239.235";
    private static final List<Host> hostList = new ArrayList<>();
    public static Host ChosenHost;

    public static void Init() {
        if (!hostList.isEmpty()) return;
        hostList.add(new Host("Vyes PC Host", vyesPcHost));
        hostList.add(new Host("Vyes Laptop Host", vyesLaptopHost));
        hostList.add(new Host("VT Laptop Host", vtLaptopHost));
    }

    public static void ShowHostList() {
        System.out.println("Chọn máy chủ để kết nối:");
        for (int i = 0; i < hostList.size(); i++) {
            System.out.println((i + 1) + ". " + hostList.get(i).hostName);
        }
    }

    public static void HandleHostSelection(int choice) {
        if (choice >= hostList.size())
            System.out.println("Không hợp lệ");
        ChosenHost = hostList.get(choice - 1);
    }
}
