package fudan.pbl.mm.service;

import fudan.pbl.mm.repository.AuthorityRepository;
import fudan.pbl.mm.domain.User;
import fudan.pbl.mm.repository.UserRepository;
import fudan.pbl.mm.controller.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    @Autowired
    public AuthService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public User register(RegisterRequest request) {
        // TODO: Implement the function.
        return null;
    }

    public String login(String username, String password) {
        // TODO: Implement the function.
        return null;
    }


}
