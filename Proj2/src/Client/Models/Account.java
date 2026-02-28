package Client.Models;

import Client.Role;

public class Account {
    public Role role;
    public String password;

    public Account(Role role, String password){
        this.role = role;
        this.password = password;
    }
}
