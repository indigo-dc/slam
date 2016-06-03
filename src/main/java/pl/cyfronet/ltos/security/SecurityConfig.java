package pl.cyfronet.ltos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import pl.cyfronet.ltos.security.policy.Activity;
import pl.cyfronet.ltos.security.policy.Permissions;

/**
 * @author bwilk
 *
 */
@Configuration
@Profile("production")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	//@Autowired
	//protected PortalUserDetailsService detailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {		
		//auth.userDetailsService(detailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/", "/bootstrap-3.3.1-dist/**", "/static/**","/resources/**", "/bootstrap/**",
                        "/css/**", "/images/**", "/js/**", "/javascript/**", "/fonts/**")
                .permitAll()
			.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.and()		
				.csrf()
					.disable();
	}
//
//	@Bean
//	static PortalUserDetailsService getDetailsService() {
//		return new PortalUserDetailsService();
//	}
	
	@Bean
	static PermissionEvaluator getPermissionEvaluator() {
		return new DenyAllPermissionEvaluator();
	}
	
	@Bean
	@Autowired
	static MethodSecurityExpressionHandler getExpressionHandler(
			PermissionEvaluator evaluator, Permissions permissions) {
		SecurityExpressionHandler handler = new SecurityExpressionHandler();
		handler.setPermissionEvaluator(evaluator);
		handler.setFactory(permissions);
		return handler;
	}

	@Bean(name = "activities")
	static Activities getActivities() {
		return new Activities();
	}

	static class Activities {
		public Activity get(String name) {
			return Activity.valueOf(name);
		}
	}

}