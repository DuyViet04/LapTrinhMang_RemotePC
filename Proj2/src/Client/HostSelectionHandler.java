package Client;

public class HostSelectionHandler {
    public static String ChosenHost;

    public static void showHostList() {
        System.out.println("Chon may chu de ket noi:");
        System.out.println("1. PC Host");
        System.out.println("2. Laptop Host");
    }

//    public static void handleHostSelection(int choice) {
//        switch (choice) {
//            case 1:
//                chosenHost = HostMapper.PcHost;
//                break;
//            case 2:
//                chosenHost = HostMapper.LaptopHost;
//                break;
//            default:
//                System.out.println("Khong co may chu");
//                break;
//        }
//    }

    public static void handleHostSelection(int choice) {
        var hostList = HostMapper.getHostList();
        ChosenHost = hostList.get(choice - 1);
    }
}
