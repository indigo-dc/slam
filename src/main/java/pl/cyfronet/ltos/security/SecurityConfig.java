package pl.cyfronet.ltos.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;

import pl.cyfronet.ltos.security.permission.Role;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {	

	Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user").password("user").roles(Role.USER.toString()).and()
				.withUser("admin").password("admin").roles(Role.USER.toString(), Role.ADMIN.toString());
	}
				
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//configureDecisionManager(http);
		http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().csrf().disable();
	}
	
	void configureDecisionManager(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
            .accessDecisionManager(accessDecisionManager())
            .anyRequest()
            .permitAll();
		;
	}
		
	public AccessDecisionManager accessDecisionManager() {
		
			return new AccessDecisionManager() {
				
				@Override
				public boolean supports(Class<?> clazz) {
					return true;
				}
				
				@Override
				public boolean supports(ConfigAttribute attribute) {
					return true;
				}
				
				@Override
				public void decide(Authentication authentication, Object object,
						Collection<ConfigAttribute> configAttributes)
						throws AccessDeniedException, InsufficientAuthenticationException {
					logger.info("auth", authentication);
					logger.info("object", object);
					logger.info("attributes", configAttributes);
				}
			};
	}
	
}