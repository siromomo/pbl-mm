package fudan.pbl.mm;

import fudan.pbl.mm.domain.Authority;
import fudan.pbl.mm.domain.User;
import fudan.pbl.mm.repository.AuthorityRepository;
import fudan.pbl.mm.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                // Create authorities if not exist.
                Authority adminAuthority = getOrCreateAuthority("Admin", authorityRepository);
                Authority studentAuthority = getOrCreateAuthority("Student", authorityRepository);
                Authority teacherAuthority = getOrCreateAuthority("Teacher", authorityRepository);
                // Create an admin if not exists.
                if (userRepository.findByUsername("admin") == null) {
                    User admin = new User(
                            "admin",
                            passwordEncoder.encode("password"),
                            "hakutou",
                            20,
                            "China",
                            "Female",
                            new HashSet<>(Collections.singletonList(adminAuthority))
                    );
                    userRepository.save(admin);
                }
            }

            private Authority getOrCreateAuthority(String authorityText, AuthorityRepository authorityRepository) {
                Authority authority = authorityRepository.findByAuthority(authorityText);
                if (authority == null) {
                    authority = new Authority(authorityText);
                    authorityRepository.save(authority);
                }
                return authority;
            }
        };
    }
}

