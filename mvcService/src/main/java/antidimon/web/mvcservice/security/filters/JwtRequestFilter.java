package antidimon.web.mvcservice.security.filters;

import antidimon.web.mvcservice.repositories.MyUserRepository;
import antidimon.web.mvcservice.security.MyUser;
import antidimon.web.mvcservice.security.MyUserDetails;
import antidimon.web.mvcservice.services.JwtService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
public class JwtRequestFilter implements Filter {

    private JwtService jwtService;
    private MyUserRepository myUserRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Cookie[] cookies = request.getCookies();
        String jwt = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JWT")) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        if (jwt != null && jwtService.isValid(jwt)){
            String phoneNumber = jwtService.extractPhoneNumberFromJwt(jwt);
            if (phoneNumber != null) {
                Optional<MyUser> user = myUserRepository.findByPhoneNumber(phoneNumber);
                if (user.isPresent()) {
                    MyUserDetails userDetails = new MyUserDetails(user.get());
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);

    }




}
