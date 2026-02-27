package Server;

import java.io.*;

public class ServerRequestHandler {
    private static PrintWriter cmdWriter;
    private static BufferedReader cmdReader;

    public static void initCmd() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/k");
        pb.redirectErrorStream(true);
        Process proc = pb.start();

        cmdWriter = new PrintWriter(new OutputStreamWriter(proc.getOutputStream()), true);
        cmdReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
    }

    public static void executeCommand(String command, PrintWriter writer) throws IOException, InterruptedException {
        cmdWriter.println(command + " && echo Xong");

        String line;
        while ((line = cmdReader.readLine()) != null) {
            //if (line.equals("Xong")) break;
            writer.println(line);
            System.out.println(line);

            if (line.equals("Xong")) break;
        }
    }


}
