package pl.cyfronet.ltos.security;

import java.util.Collection;

import lombok.Builder;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.security.policy.Identity;

/**
 * @author bwilk
 *
 */
@Builder
public class PortalUser implements Authentication, Identity {

    private static final long serialVersionUID = 1L;

    private boolean isAuthenticated;
    
    private String name;
    private Object credentials;
    
    private UserInfo principal;
    private User details;
    private Collection<? extends GrantedAuthority> authorities;
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }
    
    public void setDetails(User details) {
        if (principal != null && details.getId() != null) {
            principal.setId(details.getId());
        }
        this.details = details;
    }

    @Override
    public Object getDetails() { 
        return details;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }
    
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {   
        this.isAuthenticated = isAuthenticated;
        
    }
    
    public User getUserBean() {
        return details;
    }

    @Override
    public Long getId() {
        return details.getId();
    }

}
