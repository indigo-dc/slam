package pl.cyfronet.ltos.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author bwilk
 *
 */
@Configuration
@Profile("development")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Order(2)
public class SecurityConfigDev extends WebSecurityConfigurerAdapter {
	
    static Logger log = LoggerFactory.getLogger(SecurityConfigDev.class);
    
    @Autowired
    private AuthenticationProviderDev provider;
    
    @Bean
    static AuthenticationProviderDev getCustomAuthenticationProvider() {
        return new AuthenticationProviderDev();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/rest/**")
                .permitAll().anyRequest().authenticated()
                .antMatchers("/bootstrap-3.3.4-dist/**", "/static/**",
                        "/resources/**", "/css/**",
                        "/images/**", "/js/**", "/lib/**", "/fonts/**")
                .permitAll().anyRequest().authenticated().and().formLogin()
                .and().csrf().disable();
    }
 
}