package pl.cyfronet.ltos.security.policy;

import lombok.Setter;

public abstract class AbstractSecurityPolicy<T> implements SecurityPolicy {
	
	@Setter
	protected Identity identity;
	@Setter
	protected T targetObject;
	@Setter
	protected Activity activity;
	
	@Override
	abstract public boolean evaluate();
	
}
