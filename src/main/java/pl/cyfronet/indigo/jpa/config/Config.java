package pl.cyfronet.indigo.jpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.cyfronet.indigo.repository.RepositoryConfig;

/**
 * Created by km on 10.06.16.
 */
@Configuration
@Import( {DataSourceConfig.class, JpaConfig.class, RepositoryConfig.class} )
public class Config {
}
