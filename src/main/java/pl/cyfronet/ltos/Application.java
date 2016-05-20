package pl.cyfronet.ltos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;

import pl.cyfronet.ltos.security.Activities;
import pl.cyfronet.ltos.security.LtosSecurityExpressionHandler;
import pl.cyfronet.ltos.security.RoleBasedSecurityPolicy;
import pl.cyfronet.ltos.security.permission.Activity;

@ComponentScan("pl.cyfronet")
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	static PermissionEvaluator getPermissionEvaluator() {
		return new RoleBasedSecurityPolicy();
	}

	@Bean
	@Autowired
	static MethodSecurityExpressionHandler getExpressionHandler(
			PermissionEvaluator evaluator) {
		//DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
		LtosSecurityExpressionHandler handler = new LtosSecurityExpressionHandler();
		handler.setPermissionEvaluator(evaluator);
		return handler;
	}

	@Bean(name={"authActs"})
	static Activities getAuthorizedActivities() {
		return new Activities();
	}

}