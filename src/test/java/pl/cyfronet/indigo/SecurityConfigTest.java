package pl.cyfronet.indigo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import pl.cyfronet.indigo.security.PortalUser;
import pl.cyfronet.indigo.security.SecurityConfig;

/**
 * @author bwilk
 *
 */
@Configuration
@Profile("test")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigTest extends SecurityConfig {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new AuthenticationProvider() {
            
            @Override
            public boolean supports(Class<?> authentication) {
                return authentication.isAssignableFrom(PortalUser.class);
            }
            
            @Override
            public Authentication authenticate(Authentication authentication)
                    throws AuthenticationException {
                return authentication;
            }
        });
    }

}