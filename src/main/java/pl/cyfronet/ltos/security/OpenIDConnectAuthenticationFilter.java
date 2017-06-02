package pl.cyfronet.ltos.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.agreemount.bean.identity.Identity;
import com.agreemount.bean.identity.provider.IdentityProvider;
import com.google.common.base.Preconditions;

import pl.cyfronet.ltos.bean.Role;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.repository.UserRepository;
import pl.cyfronet.ltos.security.AuthenticationProviderDev.UserOperations;

/**
 * Created by km on 04.08.16.
 */
public class OpenIDConnectAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Value("${unity.server.base}")
    private String authorizeUrl;

    @Value("${unity.unauthorizedAction}")
    private String unauthorizedAction;

    @Value("${hostname.verification}")
    private Boolean hostnameVerification;

    @Value("${unity.server.userInfoAction}")
    private String userInfoAction;

    @Autowired
    private OAuth2RestOperations restTemplate;

    @Autowired
    IdentityProvider identityProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserOperations userOperations;

    //development variable, users whose email matches this will have provider role assigned
    @Value("${provider.email:null}")
    private String providerEmail;

    protected OpenIDConnectAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);

        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl("/");
        successHandler.setAlwaysUseDefaultTargetUrl(true);

        setAuthenticationSuccessHandler(successHandler);
        setAuthenticationFailureHandler(new OpenIDConnectAuthenticationFailureHandler());

        setAuthenticationManager(authentication -> authentication);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        logger.error(request.toString() + " COKOLWIEK " + response.toString());
        if (hostnameVerification.equals(false)) {
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> {
                logger.warn("Hostname verification is disabled!!!");
                return true;
            });
        }

        PortalUser.PortalUserBuilder builder = PortalUser.builder();
        try {
            //TODO: poprawne odparsowanie i debugowanie
            UserInfo userInfo = restTemplate.getForObject(authorizeUrl + userInfoAction, UserInfo.class);
            builder.isAuthenticated(true);
            User user = userRepository.findByEmail(userInfo.getEmail());
            if (user == null) {
                user = User.builder().name(userInfo.getName()).email(userInfo.getEmail())
                        .organisationName(userInfo.getOrganisation_name())
                        .roles(Arrays.asList(userOperations.loadOrCreateRoleByName("manager")))
                        .build();

                userRepository.save(user);
            }
            Role providerRole = userOperations.loadOrCreateRoleByName("provider");
            if (user.getEmail().equals(providerEmail) && !user.hasRole("provider")){
                if (!user.getRoles().contains(providerRole)) {
                    user.getRoles().add(providerRole);
                    userRepository.save(user);
                }
            }
            else { //remove if not in settings
                ArrayList<Role> elementsToRemove = new ArrayList<>();
                for (Role role : user.getRoles()){
                    if (role.getName().equals(providerRole.getName())){
                        elementsToRemove.add(role);
                    }
                }
                if(elementsToRemove.size() > 0) {
                    user.getRoles().removeAll(elementsToRemove);
                    userRepository.save(user);
                }
            }

            builder.user(user);
            userInfo.setId(user.getId());
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();

            for(Role role : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName().toUpperCase()));
            }

            builder.authorities(authorities);

            Identity identity = getIdentity(user);
            Preconditions.checkNotNull(identity, "Identity [%s] was not found", user.getEmail());
            identityProvider.setIdentity(identity);

            builder.principal(userInfo);
            logger.error("userinfo: "+userInfo.toString());
        } catch (UserDeniedAuthorizationException | InvalidRequestException ex) {
            RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            redirectStrategy.sendRedirect(request, response, unauthorizedAction);
        }

        return builder.build();
    }

    public Identity getIdentity(User user) {
        Identity identity = new Identity();
        identity.setLogin(user.getEmail());
        List<String> roles = user.getRoles().stream().map(entry -> entry.getName()).collect(Collectors.toList());
        identity.setRoles(roles);
//        List<TeamMembership> teams = user.getTeamMemberships();
//        List<TeamMember> teamMembers = new LinkedList<TeamMember>();
//        if (teams != null) {
//            for (TeamMembership team : teams) {
//                for (TeamRole role : team.getTeamRoles()) {
//                    TeamMember teamMember = new TeamMember(role.getName(), team.getTeam().getName());
//                    teamMembers.add(teamMember);
//                }
//            }
//        }
//        identity.setTeamMembers(teamMembers);
        return identity;
    }
}

