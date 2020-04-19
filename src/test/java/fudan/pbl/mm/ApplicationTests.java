package fudan.pbl.mm;

import fudan.pbl.mm.controller.request.auth.LoginRequest;
import fudan.pbl.mm.controller.request.auth.RegisterRequest;
import fudan.pbl.mm.controller.response.ResponseObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class ApplicationTests {
    RestTemplate template;

    @Test
    void contextLoads() {
    }

    @Test
    public void testRegisterAndLogin() {
        template = new RestTemplate();
        String username = "test2";
        String password = "test2";
        Set<String> auth = new HashSet<>();
        auth.add("Student");
        System.out.println(
                testRegister(username, password));
        ResponseEntity entity = testLogin(username, password);
        System.out.println(
                entity.getBody()
        );
        String token = ((ResponseObject) entity.getBody()).getMessage();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("jwt_token", token);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(null, httpHeaders);
        System.out.println(template.exchange("http://localhost:8080/test", HttpMethod.GET,
                httpEntity, String.class));
    }

    private String testRegister(String username, String password){
        RegisterRequest registerRequest = new RegisterRequest(username, password, "a@b.com");
        return template.postForEntity("http://localhost:8080/register",
                registerRequest, String.class).getBody();
    }

    private ResponseEntity testLogin(String username, String password){
        LoginRequest loginRequest = new LoginRequest(username, password);
        return template.postForEntity("http://localhost:8080/login",
                loginRequest, ResponseObject.class);
    }

}
