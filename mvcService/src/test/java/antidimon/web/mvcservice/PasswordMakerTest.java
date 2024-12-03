package antidimon.web.mvcservice;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordMakerTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);
    @Test
    public void test(){
        System.out.println(passwordEncoder.encode("123"));
    }
}
