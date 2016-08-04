package pl.cyfronet.ltos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
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
    @Value("${unity.entryPointUnityUrl}")
    private String entryPointUnityUrl;

    @Value("${unity.entryPointAuthUrl}")
    private String entryPointAuthUrl;

    @Value("${unity.unauthorizedAction}")
    private String unauthorizedAction;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint(entryPointAuthUrl);
    }

    @Bean
    public OpenIDConnectAuthenticationFilter openIdConnectAuthenticationFilter() {
        return new OpenIDConnectAuthenticationFilter(entryPointUnityUrl);
    }

    @Bean
    public OAuth2ClientContextFilter oAuth2ClientContextFilter() {
        return new OAuth2ClientContextFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(entryPointAuthUrl, unauthorizedAction, "/bootstrap-3.3.1-dist/**", "/static/**","/resources/**", "/bootstrap/**",
                        "/css/**", "/images/**", "/js/**", "/javascript/**", "/fonts/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(oAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                .addFilterAfter(openIdConnectAuthenticationFilter(), OAuth2ClientContextFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
        ;
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