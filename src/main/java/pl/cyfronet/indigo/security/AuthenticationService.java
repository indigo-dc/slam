package pl.cyfronet.indigo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import pl.cyfronet.indigo.bean.User;
import pl.cyfronet.indigo.repository.UserRepository;
import pl.cyfronet.indigo.security.AuthenticationProviderDev.UserOperations;

import javax.net.ssl.HttpsURLConnection;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthenticationService {
    private static String AUTHORITY_ROLE_PREFIX = "ROLE_";

    @Value("${unity.server.base}")
    private String authorizeUrl;

    @Value("${unity.server.userInfoAction}")
    private String userInfoAction;

    @Value("${hostname.verification}")
    private Boolean hostnameVerification;

    @Autowired
    private OAuth2RestOperations restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserOperations userOperations;

    @Autowired
    private PortalUserFactory portalUserFactory;

    public PortalUser getPortalUser() {
        return getPortalUser(getUserInfo());
    }

    private PortalUser getPortalUser(UserInfo userInfo) {
        User user = getUser(userInfo);
        userInfo.setId(user.getId());

        PortalUserImpl.Data.DataBuilder builder = PortalUserImpl.Data.builder();
        builder.authenticated(true);
        builder.user(user);
        builder.authorities(user.getRoles().stream()
                .map(role -> AUTHORITY_ROLE_PREFIX + role.getName().toUpperCase(Locale.US))
                .map(name -> new SimpleGrantedAuthority(name))
                .collect(Collectors.toList()));
        builder.principal(userInfo);

        return portalUserFactory.createPortalUser(builder.build());
    }

    private UserInfo getUserInfo() {
        if (hostnameVerification.equals(false)) {
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> {
                log.warn("Hostname verification is disabled!!!");
                return true;
            });
        }

        return restTemplate.getForObject(authorizeUrl + userInfoAction, UserInfo.class);
    }

    private User getUser(UserInfo userInfo) {
        User user = userRepository.findByEmail(userInfo.getEmail());
        if (user == null) {
            user = User.builder().name(userInfo.getName()).email(userInfo.getEmail())
                    .organisationName(userInfo.getOrganisation_name())
                    .roles(Arrays.asList(userOperations.loadOrCreateRoleByName("manager"))).build();

            userRepository.save(user);
        }
        return user;
    }
}
