package pl.cyfronet.ltos.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import pl.cyfronet.ltos.repository.validation.UserValidator;

/**
 * @author bwilk
 *
 */
@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {

	@Autowired 
	UserValidator userValidator;
	
	@Bean
	public UserValidator userValidator() {
		return new UserValidator();
	}

	@Override
	public void configureValidatingRepositoryEventListener(
			ValidatingRepositoryEventListener validatingListener) {
		validatingListener.addValidator("beforeCreate", userValidator);
		validatingListener.addValidator("beforeSave", userValidator);
	}

}
