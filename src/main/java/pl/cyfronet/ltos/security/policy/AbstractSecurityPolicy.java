package pl.cyfronet.ltos.security.policy;

import java.util.Collection;

import lombok.Setter;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * @author bwilk
 *
 */
public abstract class AbstractSecurityPolicy<T> implements SecurityPolicy {
	
	@Setter
	protected Identity identity;
	@Setter
	protected T targetObject;
	@Setter
	protected Activity activity;
	
	@Override
	abstract public boolean evaluate();
	
	static boolean hasAccessToActivity(Activity currentActivity, Collection<Activity> allowed) {		
		for (Activity activity: allowed) {
			if (activity.equals(currentActivity)) {
				return true;
			}
		}
		return false;
	}

	static boolean hasRole(Identity identity, Role role) {
		SimpleGrantedAuthority testAuth = new SimpleGrantedAuthority("ROLE_" + role.toString());
		boolean contains = identity.getAuthorities().contains(testAuth);
		return contains;
	}
	
	static boolean isSuperuser(Identity identity) {
		return hasRole(identity, Role.ADMIN);
	}
	
}
