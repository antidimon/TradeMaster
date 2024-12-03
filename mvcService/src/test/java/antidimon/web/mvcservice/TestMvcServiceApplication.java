package antidimon.web.mvcservice;

import org.springframework.boot.SpringApplication;

public class TestMvcServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(MvcServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
