package antidimon.web.mvcservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    @Value("${jwt}")
    private String token;
    private static final long VALIDITY = TimeUnit.HOURS.toMillis(1);

    public String generateToken(UserDetails userDetails) {
        Map<String, String> claims = new HashMap<>();
        claims.put("phoneNumber", userDetails.getUsername());
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(getSecretKey())
                .compact();
    }

    private SecretKey getSecretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(token);
        return Keys.hmacShaKeyFor(decodedKey);
    }


    public String getPhoneNumber(Cookie[] cookies){
        return extractPhoneNumberFromJwt(getJwtFromCookies(cookies));
    }

    private String getJwtFromCookies(Cookie[] cookies) {
        String jwt = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("JWT")) {
                jwt = cookie.getValue();
                break;
            }
        }
        return jwt;
    }

    public String extractPhoneNumberFromJwt(String jwt) {

        try{
            Claims claims = Jwts.parser().verifyWith(getSecretKey())
                    .build().parseSignedClaims(jwt).getPayload();
            return claims.get("phoneNumber", String.class);

        }catch (Exception e){
            return null;
        }
    }

    public boolean isValid(String jwt){
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(jwt).getPayload();
            return !claims.getExpiration().before(Date.from(Instant.now()));

        } catch (ExpiredJwtException e) {
            return false;
        }
    }
}
