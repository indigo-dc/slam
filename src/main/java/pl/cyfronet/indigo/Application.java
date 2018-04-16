package pl.cyfronet.indigo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * @author bwilk
 *
 */
@ComponentScan("pl.cyfronet")
@SpringBootApplication
@PropertySources({
        @PropertySource("classpath:bootstrap.properties"),
        @PropertySource("classpath:hibernate.properties"),
        @PropertySource("classpath:auth.properties"),
        @PropertySource("classpath:bazaar.properties"),
        @PropertySource("classpath:mongo.properties")
        })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    Document factory is declared as a service, so code below is not needed anymore
//    @Bean
//    public DocumentFactory<IndigoDocument> getDocumentFactory() {
//        return new IndigoDocumentFactory();
//    }
}