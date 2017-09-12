package pl.cyfronet.ltos.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.cyfronet.ltos.bean.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableSet;

public class PortalUserImpl implements PortalUser {

    private static String AUTHORITY_ROLE_PREFIX = "ROLE_";
    private static String PROVIDER_AUTHORITY_ROLE_PREFIX = AUTHORITY_ROLE_PREFIX + "PROVIDER_";

    @Builder
    @Getter
    public static class Data {
        private boolean authenticated;

        private final String name;
        private final Object credentials;

        private final UserInfo principal;
        private final User user;
        @Singular private final Set<? extends GrantedAuthority> authorities;
    }

    private final Data data;

    private Set<? extends GrantedAuthority> authorities = null;

    @Autowired
    private CmdbOwnerService cmdbOwnerService;

    //development variable, users whose email matches this will have provider role assigned
    @Value("${provider.email:null}")
    private Set<String> devProviderEmails;

    public PortalUserImpl(Data data) {
        this.data = data;
    }

    @Override
    public String getName() {
        return data.name;
    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>(data.authorities);
        Set<String> ownedProviders = cmdbOwnerService.getOwnedProviders(data.user.getEmail());
        if (ownedProviders != null && !ownedProviders.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority(AUTHORITY_ROLE_PREFIX + "PROVIDER"));
            authorities.addAll(ownedProviders.stream()
                    .map(role -> PROVIDER_AUTHORITY_ROLE_PREFIX + role)
                    .map(authName -> new SimpleGrantedAuthority(authName))
                    .collect(Collectors.toList()));
        } else if (devProviderEmails.contains(data.user.getEmail())) {
            authorities.add(new SimpleGrantedAuthority(AUTHORITY_ROLE_PREFIX + "PROVIDER"));
        }
        return unmodifiableSet(authorities);
    }

    @Override
    public Object getCredentials() {
        return data.credentials;
    }

    @Override
    public Object getDetails() {
        return data.user;
    }

    @Override
    public Object getPrincipal() {
        return data.principal;
    }

    @Override
    public boolean isAuthenticated() {
        return data.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (!data.authenticated && isAuthenticated) {
            // following the guideline from the method's definition to only allow to invalidate Authentication
            // allowing the reverse may pose a security problem
            throw new IllegalArgumentException("You cannot set authenticated to true on an already invalidated Authentication instance. Create a new one instead.");
        }
        data.authenticated = isAuthenticated;
    }

    public User getUserBean() {
        return data.user;
    }

    @Override
    public Long getId() {
        return data.user.getId();
    }

}
