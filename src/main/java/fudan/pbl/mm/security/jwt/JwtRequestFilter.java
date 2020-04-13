package fudan.pbl.mm.security.jwt;

import fudan.pbl.mm.domain.User;
import fudan.pbl.mm.service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Write your code to make this filter works.
 *
 * @author LBW
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private JwtUserDetailsService jwtUserDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil){
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil =  jwtTokenUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            if(request.getHeader("jwt_token") != null){
                String token = request.getHeader("jwt_token");

                String username = jwtTokenUtil.getUsernameFromToken(token);
                User user = (User) jwtUserDetailsService.loadUserByUsername(username);
                if(jwtTokenUtil.validateToken(token, user)){
                    setSecurityContext(new WebAuthenticationDetailsSource().buildDetails(request), token);
                }
            }
        }catch (IllegalArgumentException e){
            logger.warn(e.getLocalizedMessage());
        }
        filterChain.doFilter(request, response);
    }

    private void setSecurityContext(WebAuthenticationDetails authDetails, String token) {
        final String username = jwtTokenUtil.getUsernameFromToken(token);
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        authentication.setDetails(authDetails);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
