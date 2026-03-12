package Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerGUI extends JFrame {
    private static final int port = 5000;
    
    private final JButton startBtn;
    private final JLabel statusLabel;
    private final JTextArea logArea;
    
    private ServerSocket serverSocket;
    private Thread serverAcceptThread;
    private boolean isRunning = false;

    // Khởi tạo giao diện chính
    public ServerGUI() {
        setTitle("Server Control Panel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Thêm Listener để xử lý khi người dùng đóng cửa sổ (X)
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopServer();
            }
        });
        
        // --- Top Panel ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
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
            logArea.append(message + "\n");
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
            statusLabel.setForeground(Color.GREEN);
            
            appendLog("Server đã khởi động ở port " + port + ". Đang lắng nghe kết nối...");
            
            // Khởi chạy Thread lắng nghe accept()
            serverAcceptThread = new Thread(() -> listenForClients());
            serverAcceptThread.start();
        } catch (Exception e) {
            appendLog("Lỗi khi khởi động Server: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Không thể khởi động Server ở port " + port, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Dừng Server Socket
    private void stopServer() {
        if (!isRunning) return;
        isRunning = false;
        try {
            if (serverSocket != null) serverSocket.close();
            appendLog("Server đã dừng. Các Client sẽ tự động ngắt kết nối.");
        } catch (Exception e) {
            appendLog("Lỗi: " + e.getMessage());
        }
        startBtn.setText("Khởi động Server");
        statusLabel.setText("Trạng thái: Đã dừng");
        statusLabel.setForeground(Color.RED);
    }
    
    // Lắng nghe và xử lý kết nối từ Client
    private void listenForClients() {
        while (isRunning) {
            try {
                Socket clientSocket = serverSocket.accept();
                String clientIP = clientSocket.getInetAddress().getHostAddress();
                
                int choice = JOptionPane.showConfirmDialog(this, "Chấp nhận kết nối từ " + clientIP + "?");
                if (choice == JOptionPane.YES_OPTION) {
                    new ServerThread(clientSocket, this).start();
                } else {
                    clientSocket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
