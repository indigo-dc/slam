package pl.cyfronet.ltos.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.validation.Validator;

import pl.cyfronet.ltos.repository.validation.UserValidator;

@Configuration
public class RepositoryValidationConfiguration extends RepositoryRestConfigurerAdapter {


	@Bean
	@Primary
	/**
	 * Create a validator to use in bean validation - primary to be able to autowire without qualifier
	 */
	public Validator validator() {
		return new UserValidator();
	}

	@Override
	public void configureValidatingRepositoryEventListener(
			ValidatingRepositoryEventListener validatingListener) {
		Validator validator = validator();
		validatingListener.addValidator("beforeCreate", validator);
		validatingListener.addValidator("beforeSave", validator);
	}

}
