package fudan.pbl.mm.controller.request.auth;

public class ModifyInfoRequest {
    private String username;
    private String region;
    private String gender;
    private String fullName;
    private String email;
    private int age;
    private String originalPassword;
    private String newPassword;

    public ModifyInfoRequest(){}
    public ModifyInfoRequest(String username, String region, String gender,
                             String fullName, String email, int age, String originalPassword,
                             String newPassword){
        this.username = username;
        this.region = region;
        this.gender = gender;
        this.fullName = fullName;
        this.email = email;
        this.age = age;
        this.originalPassword = originalPassword;
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getOriginalPassword() {
        return originalPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegion() {
        return region;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setOriginalPassword(String originalPassword) {
        this.originalPassword = originalPassword;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
