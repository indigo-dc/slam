package pl.cyfronet.ltos.security.policy;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface Identity {

	/*
	 * TODO may be changed for something spring agnostic - e.g. getRoles may suffice
	 */
	public Collection<GrantedAuthority> getAuthorities();

	public Long getId();

}
