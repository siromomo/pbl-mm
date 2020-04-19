package fudan.pbl.mm.service;

import fudan.pbl.mm.controller.request.auth.LoginRequest;
import fudan.pbl.mm.controller.request.auth.ModifyInfoRequest;
import fudan.pbl.mm.controller.response.ResponseObject;
import fudan.pbl.mm.domain.Authority;
import fudan.pbl.mm.repository.AuthorityRepository;
import fudan.pbl.mm.domain.User;
import fudan.pbl.mm.repository.UserRepository;
import fudan.pbl.mm.controller.request.auth.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    @Value("${file.uploadFolder}")
    private String FILE_BASE_PATH = "D:" + File.separator + "web3d_head_profiles";

    @Autowired
    public AuthService(UserRepository userRepository,
                       AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public ResponseObject<User> register(RegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();
        Set<String> authorities = request.getAuthorities();
        Set<Authority> authoritySet = new HashSet<>();
        for(String authority : authorities){
            if(!("Student".equals(authority) || "Teacher".equals(authority))){
                return new ResponseObject<>(404, "authority is invalid", null);
            }
            authoritySet.add(authorityRepository.findByAuthority(authority));
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setAuthorities(authoritySet);
        for(Authority authority : authoritySet){
            authority.getUsers().add(user);
            authorityRepository.save(authority);
        }
        userRepository.save(user);
        return new ResponseObject<>(200, "success", user);
    }

    public ResponseObject<User> modifyInformation(ModifyInfoRequest request){
        User user = userRepository.findByUsername(request.getUsername());
        if(user == null){
            return new ResponseObject<>(404, "user not exist", null);
        }
        if(!user.getPassword().equals(request.getOriginalPassword())){
            return new ResponseObject<>(403, "password is incorrect", null);
        }
        if(request.getEmail() != null && request.getEmail().length() > 0)
            user.setEmail(request.getEmail());
        if(request.getNewPassword() != null && request.getNewPassword().length() > 0)
            user.setPassword(request.getNewPassword());
        user.setAge(request.getAge());
        user.setFullname(request.getFullName());
        user.setGender(request.getGender());
        user.setRegion(request.getRegion());
        user.setAge(request.getAge());
        userRepository.save(user);
        return new ResponseObject<>(200, "success", user);
    }

    private ResponseObject<User> checkUserInfo(String username, String password, String fullName,
                                               int age, String region, String gender, Set<String> authorities){
        if(userRepository.findByUsername(username) != null){
            return new ResponseObject<>(404, "user exists", null);
        }
        if(username == null || username.length() == 0){
            return new ResponseObject<>(404, "username should be filled in", null);
        }
        if(password == null || password.length() == 0){
            return new ResponseObject<>(404, "password should be filled in", null);
        }
        if(fullName == null || fullName.length() == 0){
            return new ResponseObject<>(404, "full name should be filled in", null);
        }
        if(region == null || region.length() == 0){
            return new ResponseObject<>(404, "region should be filled in" ,null);
        }
        if(gender == null || gender.length() == 0){
            return new ResponseObject<>(404, "gender should be filled in", null);
        }
        if(!("female".equals(gender) || "male".equals(gender))){
            return new ResponseObject<>(404, "invalid gender", null);
        }
        if(age <= 0){
            return new ResponseObject<>(404, "invalid age", null);
        }
        if(authorities == null || authorities.size() == 0){
            return new ResponseObject<>(404, "authority should be chosen", null);
        }
        return null;
    }

    public ResponseObject<User> login(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        User user = userRepository.findByUsername(username);
        if(user == null){
            return new ResponseObject<>(404, "user does not exist", null);
        }
        if(password == null || !password.equals(user.getPassword())){
            return new ResponseObject<>(403, "password is incorrect", null);
        }
        return new ResponseObject<>(200, "success", user);
    }

    public ResponseObject setProfilePhoto(String username, MultipartFile file, String basePath){
        User user = userRepository.findByUsername(username);
        if(user == null) return new ResponseObject<>(404, "user does not exist", null);
        File base = new File(FILE_BASE_PATH);
        if(!base.exists()) base.mkdir();
        String fileName = file.getOriginalFilename();
        if(fileName == null){
            return new ResponseObject<>(404, "file does not have name", null);
        }
        if(fileName.contains(File.separator)){
            String[] temp = fileName.split("\\\\");
            fileName = temp[temp.length-1];
        }
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        if(!suffixName.equals(".jpg") && !suffixName.equals(".png") && !suffixName.equals("jpeg")
            && !suffixName.equals(".gif")){
            return new ResponseObject<>(400, "file type invalid", null);
        }
        fileName = UUID.randomUUID().toString() + suffixName;
        String destName = FILE_BASE_PATH + fileName;
        File dest = new File(destName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            return new ResponseObject<>(500, e.getMessage(), null);
        }
        fileName = basePath + "/image/" + fileName;
        user.setHeadProfilePath(fileName);
        userRepository.save(user);
        return new ResponseObject<>(200, "success", user);
    }

    public ResponseObject<User> getUserInfo(String username){
        return new ResponseObject<>(200, "success", userRepository.findByUsername(username));
    }
}
