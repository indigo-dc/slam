package pl.cyfronet.ltos.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pl.cyfronet.ltos.security.policy.Activity;
import pl.cyfronet.ltos.security.policy.Permissions;

/**
 * @author bwilk
 *
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> {
			switch (username) {
			case "admin":
				return new PortalUser(Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")), "admin", "admin", 0L);
			case "user":
				return new PortalUser(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")), "user", "user", 1L);
			default:
				throw new UsernameNotFoundException("Username " + username
						+ " not found.");
			}
		});
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.and()
				.csrf()
					.disable();
	}

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