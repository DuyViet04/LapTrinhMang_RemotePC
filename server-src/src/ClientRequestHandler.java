import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ClientRequestHandler {
    private static BufferedReader reader;

    public static void executeRequest(String request) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", request);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    }

    public static List<String> requestResult() throws IOException {
        String line;
        List<String> result = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            result.add(line);
        }
        return result;
    }
}
