package pl.cyfronet.ltos.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;

import com.agreemount.bean.identity.Identity;
import com.agreemount.bean.identity.provider.IdentityProvider;

import pl.cyfronet.ltos.bean.Role;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.repository.UserRepository;
import pl.cyfronet.ltos.security.AuthenticationProviderDev.UserOperations;

@Service
public class AuthenticationService {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

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
    IdentityProvider identityProvider;

    //development variable, users whose email matches this will have provider role assigned
    @Value("${provider.email:null}")
    private String providerEmail;

    public void engineLogin(User user) {
        Identity identity = new Identity();
        identity.setLogin(user.getEmail());
        identity.setRoles(user.getRoles().stream().map(entry -> entry.getName()).collect(Collectors.toList()));


        identityProvider.setIdentity(identity);
    }

    public PortalUser getPortalUser() {
        return getPortalUser(getUserInfo());
    }

    private PortalUser getPortalUser(UserInfo userInfo) {
        User user = getUser(userInfo);
        userInfo.setId(user.getId());

        PortalUser.PortalUserBuilder builder = PortalUser.builder();
        builder.isAuthenticated(true);
        builder.user(user);
        builder.authorities(getRoles(user));
        builder.principal(userInfo);

        return builder.build();
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

        Role providerRole = userOperations.loadOrCreateRoleByName("provider");
        if (user.getEmail().equals(providerEmail) && !user.hasRole("provider")) {
            if (!user.getRoles().contains(providerRole)) {
                user.getRoles().add(providerRole);
                userRepository.save(user);
            }
        } else { // remove if not in settings
            ArrayList<Role> elementsToRemove = new ArrayList<>();
            for (Role role : user.getRoles()) {
                if (role.getName().equals(providerRole.getName())) {
                    elementsToRemove.add(role);
                }
            }
            if (elementsToRemove.size() > 0) {
                user.getRoles().removeAll(elementsToRemove);
                userRepository.save(user);
            }
        }

        return user;
    }

    private List<SimpleGrantedAuthority> getRoles(User user) {
       return user.getRoles().stream()
               .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName().toUpperCase()))
               .collect(Collectors.toList());
    }
}
