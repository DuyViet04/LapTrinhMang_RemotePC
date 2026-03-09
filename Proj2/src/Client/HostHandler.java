package Client;

import java.util.ArrayList;
import java.util.List;

public class HostHandler {
    private final String vyesPcHost = "10.10.239.176";
    private final String vyesLaptopHost = "10.10.239.119";
    private final String vtLaptopHost = "10.10.239.235";
    private final List<Host> hostList = new ArrayList<>();

    // Khởi tạo danh sách host
    public HostHandler() {
        hostList.add(new Host("Vyes PC Host", vyesPcHost));
        hostList.add(new Host("Vyes Laptop Host", vyesLaptopHost));
        hostList.add(new Host("VT Laptop Host", vtLaptopHost));
    }

    public List<Host> getHostList() {
        return hostList;
    }

    // Hiển thị danh sách máy chủ
    public void showHostList() {
        System.out.println("Chọn máy chủ để kết nối: ");
        for (int i = 0; i < hostList.size(); i++) {
            System.out.println((i + 1) + ". " + hostList.get(i).hostName);
        }
    }

    // Xử lý lựa chọn máy chủ
    public Host handleHostSelection(int choice) {
        if (choice <= 0 || choice > hostList.size()) {
            System.out.println("Lựa chọn host không hợp lệ");
            return null;
        }
        return hostList.get(choice - 1);
    }
}
