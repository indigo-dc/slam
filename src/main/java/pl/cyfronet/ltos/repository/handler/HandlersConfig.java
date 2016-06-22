package pl.cyfronet.ltos.repository.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bwilk
 *
 */
@Configuration
public class HandlersConfig {

    @Bean
    UserEventHandler getUserEventHandler() {
        return new UserEventHandler();
    }
    
    @Bean
    AffiliationEventHandler getAffiliationEventHandler() {
        return new AffiliationEventHandler();
    }

}
