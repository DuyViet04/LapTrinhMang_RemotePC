import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();
        System.out.println("Client da ket noi, di chi: " + socket.getInetAddress());

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            String request;
            while ((request = reader.readLine()) != null) {
                System.out.println(request);

                // Thuc thi request
                ClientRequestHandler.executeRequest(request, writer);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
