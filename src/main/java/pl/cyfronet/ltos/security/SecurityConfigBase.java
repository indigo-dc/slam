package pl.cyfronet.ltos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pl.cyfronet.ltos.security.policy.Activity;
import pl.cyfronet.ltos.security.policy.Permissions;

/**
 * Created by mszostak on 09.09.16.
 */
public class SecurityConfigBase extends WebSecurityConfigurerAdapter {
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
    static SecurityConfig.Activities getActivities() {
        return new SecurityConfig.Activities();
    }

    static class Activities {
        public Activity get(String name) {
            return Activity.valueOf(name);
        }
    }
}
