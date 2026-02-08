public class RequestHandler {
    public static String executionResult;

    public static void showRequestOptions() {
        System.out.println("Chon yeu cau:");
        System.out.println("1. Lay dia chi IP");
        System.out.println("2. Hien thi cac o dia");
    }

    public static void handleRequest(int choice) {
        switch (choice) {
            case 1:
                executionResult = "ipconfig";
                break;
            case 2:
                executionResult = "fsutil fsinfo drives";
                break;
            default:
                System.out.println("Lua chon khong hop le.");
                break;
        }
    }
}
