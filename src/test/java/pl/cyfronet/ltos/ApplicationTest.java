package pl.cyfronet.ltos;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ComponentScan
@ActiveProfiles({"development"})
@EnableAutoConfiguration
public class ApplicationTest{
	
}
