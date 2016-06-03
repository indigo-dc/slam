package pl.cyfronet.ltos.security.policy;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author bwilk
 *
 */
public interface Identity {

	/*
	 * TODO may be changed for something spring agnostic - e.g. getRoles may suffice
	 */
	public Collection<? extends GrantedAuthority> getAuthorities();

	public Long getId();

}
