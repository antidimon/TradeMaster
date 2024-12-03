package antidimon.web.mvcservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:.env")
public class MvcServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcServiceApplication.class, args);
    }

}
