package pl.cyfronet.ltos.permission;

import lombok.Setter;

import org.springframework.security.core.Authentication;

public abstract class AbstractSecurityPolicy<T> implements SecurityPolicy {
	
	@Setter
	protected Authentication authentication;
	@Setter
	protected T targetObject;
	@Setter
	protected Activity activity;
	
	@Override
	abstract public boolean evaluate();
	
}
