package pl.cyfronet.indigo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import pl.cyfronet.indigo.repository.validation.AffiliationValidator;
import pl.cyfronet.indigo.repository.validation.UserValidator;

/**
 * @author bwilk
 *
 */
@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {

    @Autowired
    UserValidator userValidator;
    
    @Autowired
    AffiliationValidator affiliationValidator;

    @Bean
    public UserValidator userValidator() {
        return new UserValidator();
    }
    
    @Bean
    public AffiliationValidator affiliationValidator() {
        return new AffiliationValidator();
    }

    @Override
    public void configureValidatingRepositoryEventListener(
            ValidatingRepositoryEventListener validatingListener) {
        validatingListener.addValidator("beforeCreate", userValidator);
        validatingListener.addValidator("beforeSave", userValidator);
        validatingListener.addValidator("beforeCreate", affiliationValidator);
        validatingListener.addValidator("beforeSave", affiliationValidator);
    }
    
}
