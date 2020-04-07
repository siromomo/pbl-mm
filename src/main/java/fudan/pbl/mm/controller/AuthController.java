package fudan.pbl.mm.controller;

import fudan.pbl.mm.controller.request.LoginRequest;
import fudan.pbl.mm.controller.response.ResponseObject;
import fudan.pbl.mm.domain.User;
import fudan.pbl.mm.security.jwt.JwtTokenUtil;
import fudan.pbl.mm.service.AuthService;
import fudan.pbl.mm.controller.request.RegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

}



