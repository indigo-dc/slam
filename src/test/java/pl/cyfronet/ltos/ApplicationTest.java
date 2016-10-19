package pl.cyfronet.ltos;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@PropertySources({
        @PropertySource("classpath:bazaar.test.properties"),
        @PropertySource("classpath:mongo.test.properties")
})
public class ApplicationTest{
	
}
