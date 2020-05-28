package fudan.pbl.mm.security;

import fudan.pbl.mm.controller.AuthController;
import fudan.pbl.mm.security.jwt.JwtRequestFilter;
import fudan.pbl.mm.service.JwtUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtUserDetailsService userDetailsService;
    private JwtRequestFilter jwtRequestFilter;


    @Autowired
    public SecurityConfig(JwtUserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/welcome").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/register").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/checkUsername").permitAll()
                .antMatchers("/ws/**").permitAll()
                .antMatchers("/app/**").permitAll()
                .antMatchers("/topic/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/image/**").permitAll()
                .antMatchers("/endGame").permitAll()
                .antMatchers("/cleanCurrentGame").permitAll()
                .anyRequest().authenticated();


        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // view h2-console page at `http://IP-Address:<port>/h2-console` without authentication.
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
