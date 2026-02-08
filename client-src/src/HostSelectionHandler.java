public class HostSelectionHandler {
    public static String chosenHost;

    static void showHostServerOptions() {
        System.out.println("Chon host server:");
        System.out.println("1. PC Host");
        System.out.println("2. Laptop Host");
    }

    static void handleHostSelection(int choice) {
        switch (choice) {
            case 1:
                chosenHost = HostMapper.PcHost;
                break;
            case 2:
                chosenHost = HostMapper.LaptopHost;
                break;
            default:
                System.out.println("Lua chon khong hop le.");
                break;
        }
    }
}
