import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

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
            String request;
            while ((request = reader.readLine()) != null) {
                System.out.println(request);

                // Thuc thi request
                ClientRequestHandler.executeRequest(request);

                // Ghi lai ket qua cua request
                List<String> result = ClientRequestHandler.requestResult();

                for (int i = 0; i < result.size(); i++) {
                    System.out.println(result.get(i));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
