package pl.cyfronet.ltos.security;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j
public class RestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private OAuth2ClientContext context;

    @Autowired
    private AuthenticationService authenticationService;

    private BearerTokenExtractor extractor = new BearerTokenExtractor();

    public RestAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);

        setAuthenticationManager(authentication -> authentication);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        try {
            Authentication extract = extractor.extract(request);
            if (extract != null) {
                String jwt = extract.getPrincipal().toString();
                context.setAccessToken(new DefaultOAuth2AccessToken(jwt));

                return authenticationService.getPortalUser();
            }
        } catch(Exception e) {
            log.info("Unable to authenticate to rest API - wrong token", e);
        }

        return null;
    }
}

