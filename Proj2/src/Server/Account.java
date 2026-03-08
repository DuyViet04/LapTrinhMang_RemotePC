package Server;

import Client.Role;

public class Account {
    public String role;
    public String password;

    public Account(String role, String password){
        this.role = role;
        this.password = password;
    }
}
