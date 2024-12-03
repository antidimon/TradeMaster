package antidimon.web.stocksadder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:.env")
public class StocksAdderApplication {

    public static void main(String[] args) {
        SpringApplication.run(StocksAdderApplication.class, args);
    }

}
