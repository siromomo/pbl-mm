package fudan.pbl.mm.service;

import fudan.pbl.mm.controller.request.LoginRequest;
import fudan.pbl.mm.controller.response.ResponseObject;
import fudan.pbl.mm.domain.Authority;
import fudan.pbl.mm.repository.AuthorityRepository;
import fudan.pbl.mm.domain.User;
import fudan.pbl.mm.repository.UserRepository;
import fudan.pbl.mm.controller.request.RegisterRequest;
import fudan.pbl.mm.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    @Autowired
    public AuthService(UserRepository userRepository,
                       AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public ResponseObject<User> register(RegisterRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        String fullName = request.getFullname();
        int age = request.getAge();
        String region = request.getRegion();
        String gender = request.getGender();
        Set<String> authorities = request.getAuthorities();
        Set<Authority> authoritySet = new HashSet<>();
        ResponseObject<User> invalid = checkUserInfo(username, password, fullName, age, region, gender, authorities);
        if(invalid != null) return invalid;
        for(String authority : authorities){
            if(!("Student".equals(authority) || "Teacher".equals(authority))){
                return new ResponseObject<>(404, "authority is invalid", null);
            }
            authoritySet.add(authorityRepository.findByAuthority(authority));
        }
        User user = new User(username, password, fullName, age, region, gender, authoritySet);
        for(Authority authority : authoritySet){
            authority.getUsers().add(user);
            authorityRepository.save(authority);
        }
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
            return new ResponseObject<>(404, "fullname should be filled in", null);
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


}
