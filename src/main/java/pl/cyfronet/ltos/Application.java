package pl.cyfronet.ltos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @author bwilk
 *
 */
@ComponentScan("pl.cyfronet")
@SpringBootApplication
@PropertySource("classpath:bootstrap.properties")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}