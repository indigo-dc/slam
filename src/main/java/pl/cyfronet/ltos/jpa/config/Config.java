package pl.cyfronet.ltos.jpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.cyfronet.ltos.repository.RepositoryConfig;

/**
 * Created by km on 10.06.16.
 */
@Configuration
@Import( {DataSourceConfig.class, JpaConfig.class, RepositoryConfig.class} )
public class Config {
}
