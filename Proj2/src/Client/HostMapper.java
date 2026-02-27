package Client;

import java.util.ArrayList;
import java.util.List;

public class HostMapper {
    private static String pcHost = "10.10.239.176";
    private static String laptopHost = "10.10.239.119";
    public static List<String> HostList = new ArrayList<>();

    public static List<String> getHostList() {
        customAdd(pcHost);
        customAdd(laptopHost);
        return HostList;
    }

    static void customAdd(String hostString) {
        if (!HostList.contains(hostString)) {
            HostList.add(hostString);
        }
    }
}
