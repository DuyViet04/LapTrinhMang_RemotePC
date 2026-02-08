import java.io.*;

import static java.lang.IO.println;

public class ClientRequestHandler {
    private static BufferedReader reader;

    public static void executeRequest(String request, PrintWriter writer) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", request);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            writer.println(line);
        }
        writer.println("Xong");
        process.waitFor();
    }
}
