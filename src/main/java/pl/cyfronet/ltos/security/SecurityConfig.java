package pl.cyfronet.ltos.security;

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

import pl.cyfronet.ltos.permission.Permissions;
import pl.cyfronet.ltos.permission.Role;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication()
			.withUser("user")
				.password("user")
				.roles(Role.USER.toString())
			.and()
			.withUser("admin")
				.password("admin")
				.roles(Role.USER.toString(), Role.ADMIN.toString());
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

}