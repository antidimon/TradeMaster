package antidimon.web.restservice;

import org.springframework.boot.SpringApplication;

public class TestRestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(RestServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
