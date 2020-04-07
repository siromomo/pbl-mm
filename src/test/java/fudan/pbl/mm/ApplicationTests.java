package fudan.pbl.mm;

import fudan.pbl.mm.controller.request.LoginRequest;
import fudan.pbl.mm.controller.request.RegisterRequest;
import fudan.pbl.mm.controller.response.ResponseObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class ApplicationTests {
    RestTemplate template;

    @Test
    void contextLoads() {
    }

    @Test
    public void testRegisterAndLogin(){
        template = new RestTemplate();
        String username = "test";
        String password = "test";
        Set<String> auth = new HashSet<>();
        auth.add("Student");
        System.out.println(
                testRegister(username, password, "test", 20, "China", "male", auth));
        ResponseEntity entity = testLogin(username, password);
        System.out.println(
                entity.getBody()
        );
        String token = ((ResponseObject) entity.getBody()).getMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("jwt_token", token);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(null, httpHeaders);
        System.out.println(template.exchange("http://localhost:8080/test",HttpMethod.GET,
                httpEntity, String.class));
    }

    private String testRegister(String username, String password, String fullname, int age,
                                String region, String gender, Set<String> authorities){
        RegisterRequest registerRequest = new RegisterRequest(username, password, fullname, age,
                region, gender, authorities);
        return template.postForEntity("http://localhost:8080/register",
                registerRequest, String.class).getBody();
    }

    private ResponseEntity testLogin(String username, String password){
        LoginRequest loginRequest = new LoginRequest(username, password);
        return template.postForEntity("http://localhost:8080/login",
                loginRequest, ResponseObject.class);
    }

}
