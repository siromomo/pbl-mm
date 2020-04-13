package fudan.pbl.mm.controller.request;

import java.util.Set;


public class RegisterRequest {
    private String username;
    private String password;
    private String fullname;
    private int age;
    private String region;
    private String gender;
    private Set<String> authorities;

    public RegisterRequest() {}

    public RegisterRequest(String username, String password, String fullname, int age,
                           String region, String gender, Set<String> authorities) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.age = age;
        this.region = region;
        this.gender = gender;
        this.authorities = authorities;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

