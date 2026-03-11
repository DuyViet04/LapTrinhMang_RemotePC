package Client;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ClientGUI extends JFrame {
    private static final int port = 5000;

    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    private final HostHandler hostHandler;

    private Host chosenHost;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    // UI
    private JTextArea outputArea;
    private JTextField extraCommandField;

    // Khởi tạo giao diện chính
    public ClientGUI() {
        hostHandler = new HostHandler();

        setTitle("Client Control Panel");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createServerSelectionPanel(), "ServerSelection");
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createControlPanel(), "Control");

        add(mainPanel);
    }

    // Tạo màn hình chọn lệnh máy chủ
    private JPanel createServerSelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Chọn máy chủ để kết nối:", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (Host host : hostHandler.getHostList()) {
            JButton btn = new JButton(host.hostName);
            btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
            btn.addActionListener(e -> {
                chosenHost = host;
                JOptionPane.showMessageDialog(this, "Đã chọn máy chủ: " + host.hostName + "\nVui lòng đăng nhập.");
                cardLayout.show(mainPanel, "Login");
            });
            buttonPanel.add(btn);
        }

        panel.add(buttonPanel, BorderLayout.CENTER);
        return panel;
    }

    // Tạo màn hình đăng nhập
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel roleLabel = new JLabel("Vai trò:");
        JComboBox<Role> roleComboBox = new JComboBox<>(Role.values());

        JLabel passLabel = new JLabel("Mật khẩu:");
        JPasswordField passField = new JPasswordField(15);

        JButton loginBtn = new JButton("Đăng nhập");
        loginBtn.addActionListener(e -> {
            Role role = (Role) roleComboBox.getSelectedItem();
            String password = new String(passField.getPassword());
            connectAndLogin(role, password);
        });

        JButton backBtn = new JButton("Quay lại");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "ServerSelection"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(roleLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(roleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passField, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        btnPanel.add(backBtn);
        btnPanel.add(loginBtn);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);

        return panel;
    }

    // Xử lý kết nối và đăng nhập tới Server
    private void connectAndLogin(Role role, String password) {
        new Thread(() -> {
            try {
                socket = new Socket(chosenHost.ipAddress, port);
                writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String loginRequest = role.name() + " " + password;
                writer.println(loginRequest);

                String response = reader.readLine();
                if ("LOGIN_SUCCESS".equals(response)) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                        cardLayout.show(mainPanel, "Control");
                    });
                } else {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "Đăng nhập thất bại: " + response, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    });
                    socket.close();
                }
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Lỗi kết nối: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
    }

    // Tạo màn hình điều khiển
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Cột bên trái: Các lệnh điều khiển và Tham số
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(250, 0));

        JPanel commandPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        commandPanel.setBorder(BorderFactory.createTitledBorder("Các lệnh điều khiển"));

        String[] commands = {
                "1. Lấy địa chỉ IP",
                "2. Xem các ổ đĩa trên máy",
                "3. Xem thông tin thư mục/ổ đĩa",
                "4. Điều hướng sang thư mục",
                "5. Điều hướng sang ổ đĩa"
        };

        for (int i = 0; i < commands.length; i++) {
            final int choice = i + 1;
            String text = commands[i];
            JButton btn = new JButton(text);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.addActionListener(e -> executeCommand(choice));
            commandPanel.add(btn);
        }

        JPanel extraPanel = new JPanel(new BorderLayout(5, 5));
        extraPanel.setBorder(BorderFactory.createTitledBorder("Tham số / Lệnh mở rộng"));
        extraCommandField = new JTextField();
        JLabel hintLabel = new JLabel("Nhập ổ đĩa/thư mục (cho lệnh 4,5):");
        hintLabel.setFont(new Font("SansSerif", Font.ITALIC, 11));
        extraPanel.add(hintLabel, BorderLayout.NORTH);
        extraPanel.add(extraCommandField, BorderLayout.CENTER);

        leftPanel.add(commandPanel, BorderLayout.NORTH);
        leftPanel.add(extraPanel, BorderLayout.CENTER);

        // Vùng xem kết quả
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Kết quả trả về"));

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Xử lý thực thi câu lệnh
    private void executeCommand(int choice) {
        String extraCommand = extraCommandField.getText().trim();
        String command = "";

        switch (choice) {
            case 1:
                command = "ipconfig";
                break;
            case 2:
                command = "fsutil fsinfo drives";
                break;
            case 3:
                command = "dir";
                break;
            case 4:
                command = "cd " + extraCommand;
                break;
            case 5:
                command = "cd /d " + extraCommand + ":/";
                break;
        }

        if (command.isEmpty()) return;

        final String finalCommand = command;
        new Thread(() -> {
            try {
                writer.println(finalCommand);
                StringBuilder result = new StringBuilder();
                result.append(">> Gửi lệnh: ").append(finalCommand).append("\n");
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().equals("Xong")) {
                        break;
                    }
                    result.append(line).append("\n");
                }
                result.append("--------------------------------------------------\n");

                SwingUtilities.invokeLater(() -> {
                    outputArea.append(result.toString());
                    outputArea.setCaretPosition(outputArea.getDocument().getLength());
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    outputArea.append("Lỗi đọc kết quả: " + ex.getMessage() + "\n");
                });
            }
        }).start();
    }
}
