package pl.cyfronet.ltos.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.cyfronet.ltos.repository.domain.UserDataOnDemand;

@Configuration
public class DataOnDemandConfiguration {

    @Bean
    public UserDataOnDemand userDataOnDemand() {
        return new UserDataOnDemand();
    }
    
}
