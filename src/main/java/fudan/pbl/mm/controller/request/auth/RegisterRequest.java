package fudan.pbl.mm.controller.request.auth;

import java.util.HashSet;
import java.util.Set;


public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private Set<String> authorities;

    public RegisterRequest() {}

    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.authorities = new HashSet<>();
        authorities.add("Student");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

