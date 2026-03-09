package Server;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerGUI extends JFrame {
    private static final int port = 5000;
    
    private JButton startBtn;
    private JLabel statusLabel;
    private JTextArea logArea;
    
    private ServerSocket serverSocket;
    private Thread serverAcceptThread;
    private boolean isRunning = false;

    // Khởi tạo giao diện chính
    public ServerGUI() {
        setTitle("Server Control Panel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // --- Top Panel ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        startBtn = new JButton("Khởi động Server");
        statusLabel = new JLabel("Trạng thái: Đã dừng");
        statusLabel.setForeground(Color.RED);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        startBtn.addActionListener(e -> toggleServer());
        
        topPanel.add(startBtn);
        topPanel.add(statusLabel);
        
        // --- Center Panel (Logs) ---
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("System Logs"));
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    // Thêm log hệ thống vào màn hình hiển thị
    public void appendLog(String message) {
        SwingUtilities.invokeLater(() -> {
            String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
            logArea.append("[" + timeStamp + "] " + message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    // Xử lý sự kiện bật/tắt Server
    private void toggleServer() {
        if (!isRunning) {
            startServer();
        } else {
            stopServer();
        }
    }

    // Khởi động Server Socket
    private void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            isRunning = true;
            
            startBtn.setText("Dừng Server");
            statusLabel.setText("Trạng thái: Đang chạy (Port " + port + ")");
            statusLabel.setForeground(new Color(0, 153, 0)); // Xanh lá cây sẫm
            
            appendLog("Server đã khởi động ở port " + port + ". Đang lắng nghe kết nối...");
            
            // Khởi chạy Thread lắng nghe accept()
            serverAcceptThread = new Thread(() -> listenForClients());
            serverAcceptThread.start();
        } catch (IOException e) {
            appendLog("Lỗi khi khởi động Server: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Không thể khởi động Server ở port " + port, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Dừng Server Socket
    private void stopServer() {
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            appendLog("Lỗi khi đóng ServerSocket: " + e.getMessage());
        }
        
        startBtn.setText("Khởi động Server");
        statusLabel.setText("Trạng thái: Đã dừng");
        statusLabel.setForeground(Color.RED);
        appendLog("Server đã dừng.");
    }
    
    // Lắng nghe và xử lý kết nối từ Client
    private void listenForClients() {
        while (isRunning) {
            try {
                Socket clientSocket = serverSocket.accept();
                String clientIP = clientSocket.getInetAddress().getHostAddress();
                appendLog("Có client muốn kết nối từ IP: " + clientIP);
                
                // Mở hộp thoại hỏi Admin có cho phép không
                SwingUtilities.invokeLater(() -> {
                    int choice = JOptionPane.showConfirmDialog(
                            this,
                            "Client IP " + clientIP + " đang yêu cầu kết nối.\nBạn có muốn chấp nhận không?",
                            "Yêu cầu kết nối",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    
                    if (choice == JOptionPane.YES_OPTION) {
                        appendLog("Đã chấp nhận kết nối từ " + clientIP + ". Chờ đăng nhập...");
                        // Bắt đầu luồng cho Client mới
                        new ServerThread(clientSocket, this).start();
                    } else {
                        appendLog("Đã từ chối kết nối từ " + clientIP + ".");
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (IOException e) {
                if (isRunning) {
                    appendLog("Lỗi khi chờ client: " + e.getMessage());
                }
            }
        }
    }
}
