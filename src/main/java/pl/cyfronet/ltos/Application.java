package pl.cyfronet.ltos;

import com.agreemount.bean.document.Document;
import com.agreemount.slaneg.action.DocumentFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import pl.cyfronet.bazaar.engine.extension.bean.IndigoDocument;

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

    @Bean
    public DocumentFactory<IndigoDocument> getDocumentFactory() {
        return new DocumentFactory<>(IndigoDocument.class);
    }
}