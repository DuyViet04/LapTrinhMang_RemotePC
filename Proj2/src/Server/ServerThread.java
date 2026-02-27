package Server;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    Socket clientSocket;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        super.run();
        System.out.println("Client da ket noi, ip: " + clientSocket.getInetAddress());

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

            ServerRequestHandler.initCmd();

            while (true) {
                String request = reader.readLine();
                System.out.println("Lenh da nhan: " + request);
                ServerRequestHandler.executeCommand(request, writer);
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
