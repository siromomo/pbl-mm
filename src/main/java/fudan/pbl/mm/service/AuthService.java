package fudan.pbl.mm.service;

import com.google.common.collect.Lists;
import fudan.pbl.mm.controller.request.auth.GameRecordRequest;
import fudan.pbl.mm.controller.request.auth.LoginRequest;
import fudan.pbl.mm.controller.request.auth.ModifyInfoRequest;
import fudan.pbl.mm.controller.response.ResponseObject;
import fudan.pbl.mm.controller.response.UserInfoResponse;
import fudan.pbl.mm.controller.response.UserProgressesResponse;
import fudan.pbl.mm.domain.*;
import fudan.pbl.mm.repository.AuthorityRepository;
import fudan.pbl.mm.repository.GameRecordRepository;
import fudan.pbl.mm.repository.PackRepository;
import fudan.pbl.mm.repository.UserRepository;
import fudan.pbl.mm.controller.request.auth.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AuthService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private GameRecordRepository gameRecordRepository;
    private PackRepository packRepository;

    @Value("${file.uploadFolder}")
    private String FILE_BASE_PATH = "D:" + File.separator + "web3d_head_profiles";
    private final static String MAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    private static BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();

    @Autowired
    public AuthService(UserRepository userRepository,
                       AuthorityRepository authorityRepository,
                       GameRecordRepository gameRecordRepository,
                       PackRepository packRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.gameRecordRepository = gameRecordRepository;
        this.packRepository = packRepository;
    }

    public ResponseObject<User> register(RegisterRequest request) {
        String username = request.getUsername();
        if(userRepository.findByUsername(username) != null){
            return new ResponseObject<>(400, "user has exist", null);
        }
        if(username.length() == 0){
            return new ResponseObject<>(400, "username should be filled", null);
        }
        String password = request.getPassword();
        if(password == null || password.length() == 0){
            return new ResponseObject<>(400, "password should be filled", null);
        }
        String email = request.getEmail();
        if(email == null || !email.matches(MAIL_REGEX)){
            return new ResponseObject<>(400, "invalid email", null);
        }
        Set<String> authorities = request.getAuthorities();
        Set<Authority> authoritySet = new HashSet<>();
        if(authorities == null){
            authoritySet.add(authorityRepository.findByAuthority("Student"));
        }else {
            for (String authority : authorities) {
                if (!("Student".equals(authority) || "Teacher".equals(authority))) {
                    return new ResponseObject<>(404, "authority is invalid", null);
                }
                authoritySet.add(authorityRepository.findByAuthority(authority));
            }
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setEmail(email);
        user.setModelId(request.getModelId());
        user.setAuthorities(authoritySet);

        userRepository.save(user);
        user = userRepository.findByUsername(username);
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
            user.setPassword(encoder.encode(request.getNewPassword()));
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
        if(password == null || !checkPwd(password, user.getPassword())){
            return new ResponseObject<>(403, "password is incorrect", null);
        }
        return new ResponseObject<>(200, "success", user);
    }

    private boolean checkPwd(String myPwd, String pwd){
        return myPwd.equals(pwd) || encoder.matches(myPwd, pwd);
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

    public ResponseObject<String> findUserById(Long userId){
        return new ResponseObject<>(200, "success", userRepository.findUserById(userId).getUsername());
    }

    public ResponseObject<?> endGame(GameRecordRequest request){
        Set<User> users = new HashSet<>();
        for(Long userId : request.getUserIds()){
            User user = userRepository.findUserById(userId);
            if(user == null){
                return new ResponseObject<>(404, "user " + userId + " not exists", null);
            }
            users.add(user);
        }
        Pack pack = packRepository.findPackById(request.getPackId());
        Set<Knowledge> knowledgeSet = pack.getKnowledgeSet();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(request.getTime(), pos);
        GameRecord record = new GameRecord(request.getStatus(), request.getScore(), date, users);
        gameRecordRepository.save(record);
        for(User user : users) {
            user.addToGameRecords(record);
            user.getKnowledgeSet().addAll(knowledgeSet);
            userRepository.save(user);
        }
        return new ResponseObject<>(200, "success", null);
    }

    public ResponseObject<Set<Knowledge>> findKnowledgeByUserId(Long id){
        User user = userRepository.findUserById(id);
        if(user == null){
            return new ResponseObject<>(404, "user not exists", null);
        }
        return new ResponseObject<>(200, "success", user.getKnowledgeSet());
    }

    public ResponseObject<List<GameRecord>> findRecordsByUserId(Long id){
        User user = userRepository.findUserById(id);
        if(user == null){
            return new ResponseObject<>(404, "user not exists", null);
        }
        return new ResponseObject<>(200, "success",
                gameRecordRepository.findGameRecordsByUsersContaining(user));
    }

    public ResponseObject<Boolean> getIsAdmin(Long id){
        User user = userRepository.findUserById(id);
        if(user == null){
            return new ResponseObject<>(404, "user not exists", null);
        }
        boolean isAdmin = false;
        for(GrantedAuthority authority : user.getAuthorities()){
            if("Admin".equals(authority.getAuthority())){
                isAdmin = true;
                break;
            }
        }
        return new ResponseObject<>(200, "success", isAdmin);
    }

    public ResponseObject<List<User>> getUserProgresses(){
        List<User> users = Lists.newArrayList(userRepository.findAll());
        for(User user : users){
            user.setKnowledgeNum(user.getKnowledgeSet().size());
            user.setGameNum(user.getGameRecords().size());
        }
        users.sort((o1, o2) -> {
            int k1 = o1.getKnowledgeNum();
            int k2 = o2.getKnowledgeNum();
            if (k1 == k2) {
                return 0;
            } else {
                return k1 > k2 ? -1 : 1;
            }
        });
        return new ResponseObject<>(200, "success", users);
    }

    public ResponseObject<User> changeModel(Long userId, int modelId){
        User user = userRepository.findUserById(userId);
        if(user == null){
            return new ResponseObject<>(404, "user not exists", null);
        }
        user.setModelId(modelId);
        userRepository.save(user);
        return new ResponseObject<>(200, "success", user);
    }

    public ResponseObject<UserInfoResponse> getUserInfos(){
        List<User> users = Lists.newArrayList(userRepository.findAll());
        UserInfoResponse response = new UserInfoResponse();
        for(User user : users){
            for(int i = 0; i < 10; i++){
                int low = i == 0 ? 0 : response.ageRange[i-1];
                int high = response.ageRange[i];
                if(user.getAge() >= low && user.getAge() < high){
                    response.ageRangeNum[i]++;
                }
            }
            if("male".equals(user.getGender().toLowerCase())){
                response.genderRangeNum[0]++;
            }else{
                response.genderRangeNum[1]++;
            }
        }
        return new ResponseObject<>(200, "success", response);
    }

    public ResponseObject<UserProgressesResponse> getUserGameTimes(){
        List<GameRecord> gameRecords = Lists.newArrayList(gameRecordRepository.findAll());
        UserProgressesResponse response = new UserProgressesResponse();
        for(GameRecord gameRecord : gameRecords){
            for(int i = 0; i < response.timeRange.length; i++){
                Date low = i == 0 ? response.strToDate("1900-01-01 00:00:00") : response.timeRange[i-1];
                Date high = response.timeRange[i];
                if(gameRecord.getTime().after(low) && gameRecord.getTime().before(high)){
                    response.timeRangeNum[i]++;
                    break;
                }
            }
        }
        return new ResponseObject<>(200, "success", response);
    }
}
