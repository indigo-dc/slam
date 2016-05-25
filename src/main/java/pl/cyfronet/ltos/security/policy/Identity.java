package pl.cyfronet.ltos.security.policy;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface Identity {

	// may be changed for something spring agnostic
	public Collection<GrantedAuthority> getAuthorities();

	public Long getId();

}
