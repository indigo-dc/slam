package pl.cyfronet.ltos.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.web.filter.OncePerRequestFilter;

public class RestAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(RestAuthenticationFilter.class);

    @Autowired
    private OAuth2ClientContext context;

    @Autowired
    private AuthenticationService authenticationService;

    private BearerTokenExtractor extractor = new BearerTokenExtractor();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        tryToAuthorize(request);
        filterChain.doFilter(request, response);
    }

    private void tryToAuthorize(HttpServletRequest request) {
        try {
            Authentication extract = extractor.extract(request);
            if (extract != null) {
                String jwt = extract.getPrincipal().toString();
                context.setAccessToken(new DefaultOAuth2AccessToken(jwt));

                PortalUser portalUser = authenticationService.getPortalUser();

                authenticationService.engineLogin(portalUser.getUserBean());
                SecurityContextHolder.getContext().setAuthentication(portalUser);
            }
        } catch(Exception e) {
            log.info("Unable to authenticate to rest API - wrong token", e);
        }
    }
}

