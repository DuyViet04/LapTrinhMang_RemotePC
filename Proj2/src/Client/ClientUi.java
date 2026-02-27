package Client;

import javax.swing.*;
import java.awt.*;

public class ClientUi extends JFrame {
    public ClientUi() {
        setTitle("Remote Command");
        setSize(960, 540);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        HostMapper.getHostList().get(0).toString();

        JButton btn = new JButton("dads");
        panel.add(btn);

        add(panel);
        setVisible(true);
    }

    static void main(String[] args) {
        SwingUtilities.invokeLater(ClientUi::new);
    }
}
