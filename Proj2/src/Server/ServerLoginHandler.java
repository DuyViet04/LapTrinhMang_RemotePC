package Server;

import java.util.ArrayList;
import java.util.List;

public class ServerLoginHandler {
    private List<Account> accounts;

    public ServerLoginHandler() {
        accounts = new ArrayList<>();
        // Khởi tạo một số tài khoản mặc định
        accounts.add(new Account("ADMIN", "1"));
        accounts.add(new Account("USER", "1"));
    }

    public boolean checkLogin(String loginRequest) {
        if (loginRequest == null) return false;
        
        String[] parts = loginRequest.trim().split(" ");
        if (parts.length != 2) return false;
        
        String roleStr = parts[0];
        String passwordStr = parts[1];

        for (Account account : accounts) {
            if (account.role.equals(roleStr) && account.password.equals(passwordStr)) {
                return true;
            }
        }
        return false;
    }
}
