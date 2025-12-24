package org.example.localserver;
import java.io.Serializable;

public class AuthRequest implements Serializable {
    private String username;
    private String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
