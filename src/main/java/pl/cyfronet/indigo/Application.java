package pl.cyfronet.indigo;

import com.agreemount.slaneg.fixtures.FileRulesProvidersConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;

/**
 * @author bwilk
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@PropertySources({
        @PropertySource("classpath:bootstrap.properties"),
        @PropertySource("classpath:hibernate.properties"),
        @PropertySource("classpath:auth.properties"),
        @PropertySource("classpath:bazaar.properties"),
        @PropertySource("classpath:mongo.properties")
        })
@ComponentScan(
        basePackages = {"com.agreemount","pl.cyfronet.indigo"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                        value = FileRulesProvidersConfiguration.class)
        }
)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}