package pl.cyfronet.ltos.controller.bazaar;

import java.util.List;

import com.agreemount.slaneg.action.ActionContext;
import com.agreemount.slaneg.action.ActionContextFactory;
import com.agreemount.slaneg.fixtures.FileRulesProvidersConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;

import com.agreemount.Response;
import com.agreemount.bean.Query;
import com.agreemount.bean.identity.Identity;
import pl.cyfronet.bazaar.engine.rules.GenericYamlProvider;

/**
 * Created by Paweł Szepieniec pawel.szepieniec@gmail.com on 13.02.15.
 */
@Configuration
@EnableAutoConfiguration
@PropertySources({ @PropertySource("classpath:bazaar.properties"),
        @PropertySource("classpath:mongo.properties") })
@ComponentScan(
        basePackages = {"pl.cyfronet.bazaar, com.agreemount"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                        value = FileRulesProvidersConfiguration.class)
        }
)
public class BazaarConfig {

    @Autowired
    @Qualifier("queriesYamlProvider")
    private GenericYamlProvider<Query> queriesYamlProvider;
    
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
    public ActionContextFactory<ActionContext> getActionContextFactory() {
        return new ActionContextFactory<>(ActionContext.class);
    }

}
