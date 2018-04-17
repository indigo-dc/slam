package pl.cyfronet.indigo.security;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * Created by km on 04.08.16.
 */
@Configuration
@EnableOAuth2Client
public class ClientApplication {

    @Value("${unity.server.authorize}")
    private String authorize;

    @Value("${unity.server.token}")
    private String token;

    @Value("${unity.server.clientId}")
    private String clientId;

    @Value("${unity.server.clientSecret}")
    private String clientSecret;

    @Value("#{'${unity.scopes}'.split(',')}")
    private List<String> scopesList;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    private OAuth2ClientContext oAuth2ClientContext;

    @Bean
    public OAuth2ProtectedResourceDetails resource() {
        AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
        resource.setClientId(clientId);
        resource.setClientSecret(clientSecret);
        resource.setUserAuthorizationUri(authorize);
        resource.setAccessTokenUri(token);
        resource.setUseCurrentUri(true);
        resource.setScope(scopesList);
        return resource;
    }

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
    public OAuth2RestOperations restTemplate() {
        return new OAuth2RestTemplate(resource(), oAuth2ClientContext);
    }


}
