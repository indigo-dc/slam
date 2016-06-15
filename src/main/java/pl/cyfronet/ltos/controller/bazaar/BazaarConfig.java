package pl.cyfronet.ltos.controller.bazaar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.agreemount.Response;
import com.agreemount.bean.Query;
import com.agreemount.bean.identity.Identity;
import com.agreemount.slaneg.db.QueryOperations;
import com.agreemount.slaneg.fixtures.GenericYamlProvider;

/**
 * Created by Paweł Szepieniec pawel.szepieniec@gmail.com on 13.02.15.
 */
@Configuration
@EnableAutoConfiguration
@PropertySources({ @PropertySource("classpath:bazaar.properties"),
        @PropertySource("classpath:mongo.properties") })
@ComponentScan("com.agreemount")
public class BazaarConfig {

    @Autowired
    private QueryOperations queryOperations;

    @Autowired
    @Qualifier("queriesYamlProvider")
    private GenericYamlProvider<Query> queriesYamlProvider;

    @Value("${rules.loadOnStartup}")
    private boolean loadRulesOnStartup;
    
    @Value("${engine.dummyLogin}")
    private String dummyLogin;

    @Bean(name = "dummyLogin")
    public String dummyLogin() {
        return dummyLogin;
    }
    
    @Bean(name = "identitiesYamlProvider")
    public GenericYamlProvider<Identity> metricGenericYamlProvider() {
        return new GenericYamlProvider<>("identities");
    }

    @Bean
    public Response prepareRules() {

        if (loadRulesOnStartup) {
            List<Query> queries = queriesYamlProvider.getItems();
            queryOperations.storeQueries(queries);
        }

        return new Response();
    }

}
