package Server;

import java.io.*;

public class ServerRequestHandler {
    private PrintWriter cmdWriter;
    private BufferedReader cmdReader;

    public void initCmd() throws IOException {
        // Chạy chương trình CMD
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/k");
        pb.redirectErrorStream(true);
        Process proc = pb.start();

        cmdWriter = new PrintWriter(new OutputStreamWriter(proc.getOutputStream()), true);
        cmdReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
    }

    public void executeCommand(String command, PrintWriter writer) throws IOException, InterruptedException {
        // Ghi lệnh vào CMD và thực hiện lệnh
        cmdWriter.println(command + " & echo Xong");

        // Ghi lại kết quả sau khi thực hiện lệnh
        String line;
        while ((line = cmdReader.readLine()) != null) {
            writer.println(line);
            System.out.println(line);

            if (line.equals("Xong")) break;
        }
    }


}
