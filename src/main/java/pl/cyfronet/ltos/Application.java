package pl.cyfronet.ltos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;

import pl.cyfronet.ltos.security.Activities;
import pl.cyfronet.ltos.security.SecurityExpressionHandler;

@ComponentScan("pl.cyfronet")
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	static PermissionEvaluator getPermissionEvaluator() {
		return new DenyAllPermissionEvaluator();
	}

	@Bean
	@Autowired
	static MethodSecurityExpressionHandler getExpressionHandler(
			PermissionEvaluator evaluator) {
		//DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
		SecurityExpressionHandler handler = new SecurityExpressionHandler();
		handler.setPermissionEvaluator(evaluator);
		return handler;
	}

	@Bean(name={"activities"})
	static Activities getAuthorizedActivities() {
		return new Activities();
	}

}