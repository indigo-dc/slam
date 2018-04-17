package pl.cyfronet.indigo.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.cyfronet.indigo.repository.domain.AffiliationDataOnDemand;
import pl.cyfronet.indigo.repository.domain.UserDataOnDemand;

@Configuration
public class DataOnDemandConfiguration {

	@Bean
	public UserDataOnDemand userDataOnDemand() {
		return new UserDataOnDemand();
	}

	@Bean
	public AffiliationDataOnDemand affiliationDataOnDemand() {
		return new AffiliationDataOnDemand();
	}

}
