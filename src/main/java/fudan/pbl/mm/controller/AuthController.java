package fudan.pbl.mm.controller;

import fudan.pbl.mm.controller.request.auth.GameRecordRequest;
import fudan.pbl.mm.controller.request.auth.LoginRequest;
import fudan.pbl.mm.controller.request.auth.ModifyInfoRequest;
import fudan.pbl.mm.controller.response.ResponseObject;
import fudan.pbl.mm.domain.User;
import fudan.pbl.mm.security.jwt.JwtTokenUtil;
import fudan.pbl.mm.service.AuthService;
import fudan.pbl.mm.controller.request.auth.RegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private AuthService authService;
    private JwtTokenUtil jwtTokenUtil;

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService, JwtTokenUtil jwtTokenUtil) {
        this.authService = authService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        logger.debug("RegistrationForm: " + request.toString());

        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        logger.debug("LoginForm: " + request.toString());
        ResponseObject<User> responseObject =  authService.login(request);
        if(responseObject.getCode() != 200){
            return ResponseEntity.ok(responseObject);
        }
        responseObject.setMessage(jwtTokenUtil.generateToken(responseObject.getContent()));

        return ResponseEntity.ok(responseObject);
    }

    @GetMapping("/welcome")
    public ResponseEntity<?> welcome() {
        Map<String, String> response = new HashMap<>();
        String message = "Welcome. ";
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok("test");
    }

    @RequestMapping("/setProfilePhoto")
    public ResponseEntity<?> setProfilePhoto(@RequestParam MultipartFile file, @RequestHeader String jwt_token,
                                             HttpServletRequest request){
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        String username = jwtTokenUtil.getUsernameFromToken(jwt_token);
        if(username == null){
            return ResponseEntity.ok(new ResponseObject<>(404, "jwt token invalid", null));
        }
        return ResponseEntity.ok(authService.setProfilePhoto(username, file, basePath));
    }

    @RequestMapping("/getUserInfo")
    public ResponseEntity<?> getUserInfo(@RequestHeader String jwt_token){
        String username = jwtTokenUtil.getUsernameFromToken(jwt_token);
        if(username == null){
            return ResponseEntity.ok(new ResponseObject<>(404, "jwt token invalid", null));
        }
        return ResponseEntity.ok(authService.getUserInfo(username));
    }

    @RequestMapping("/modifyInformation")
    public ResponseEntity<?> modifyInformation(@RequestBody ModifyInfoRequest request){
        return ResponseEntity.ok(authService.modifyInformation(request));
    }

    @PostMapping("/endGame")
    public ResponseEntity<?> endGame(@RequestBody GameRecordRequest recordRequest){
        return ResponseEntity.ok(authService.endGame(recordRequest));
    }

    @RequestMapping("/findUserById")
    public ResponseEntity<?> findUserById(@RequestParam Long userId){
        return ResponseEntity.ok(authService.findUserById(userId));
    }

}



