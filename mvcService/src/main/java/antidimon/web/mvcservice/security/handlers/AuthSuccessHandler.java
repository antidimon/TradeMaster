package antidimon.web.mvcservice.security.handlers;

import antidimon.web.mvcservice.security.MyUserDetails;
import antidimon.web.mvcservice.services.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        Cookie cookie = new Cookie("JWT", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int)TimeUnit.HOURS.toSeconds(1));
        response.addCookie(cookie);
        response.sendRedirect("/");
    }

}
