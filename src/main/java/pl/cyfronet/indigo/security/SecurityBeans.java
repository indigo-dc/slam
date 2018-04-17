package pl.cyfronet.indigo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;

import pl.cyfronet.indigo.security.policy.Activity;
import pl.cyfronet.indigo.security.policy.Permissions;

/**
 * Created by mszostak on 09.09.16.
 */
public class SecurityBeans {
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
