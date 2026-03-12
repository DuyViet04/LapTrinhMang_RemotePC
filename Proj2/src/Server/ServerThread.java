package Server;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket clientSocket;
    private ServerGUI gui;
    private ServerRequestHandler serverRequestHandler;

    public ServerThread(Socket clientSocket, ServerGUI gui) {
        this.clientSocket = clientSocket;
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            serverRequestHandler = new ServerRequestHandler();
            ServerLoginHandler serverLoginHandler = new ServerLoginHandler();

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

            String loginRequest = reader.readLine();
            if (loginRequest != null && serverLoginHandler.checkLogin(loginRequest)) {
                writer.println("LOGIN_SUCCESS");
                serverRequestHandler.initCmd();
                
                String request;
                while ((request = reader.readLine()) != null) {
                    serverRequestHandler.executeCommand(request, writer);
                }
            } else {
                writer.println("LOGIN_FAIL");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverRequestHandler != null) serverRequestHandler.close();
            try { clientSocket.close(); } catch (IOException e) {}
            gui.appendLog("Đã đóng kết nối Client.");
        }
    }
}
