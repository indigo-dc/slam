package pl.cyfronet.ltos.security.permission;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class DefaultSecurityPolicy<T> implements SecurityPolicy {
	
	private Activity[] userPermissions = { Activity.VIEW_USER,
			Activity.SAVE_USER, 
			Activity.VIEW_AFFILIATION,
			Activity.SAVE_AFFILIATION,
			Activity.LIST_AFFILIATIONS };
	
	protected Authentication identity;
	protected T targetObject;
	protected Activity activity;
	
	public DefaultSecurityPolicy(Authentication identity, Activity activity) {
		this(identity, null, activity);
	}
	
	public DefaultSecurityPolicy(Authentication identity, T targetObject, Activity activity) {
		this.identity = identity;
		this.targetObject = targetObject;
		this.activity = activity;
	}
	
	@Override
	public boolean evaluate() {
		if (hasRole(identity, Role.ADMIN)) {
			return true;
		} 
		if (hasRole(identity, Role.USER)) {			
			if (hasAccessToActivity(activity, userPermissions)) {
				if(targetObject == null) {
					return true;
				} else {
					return extensionPoint();
				}
			}
		}
		return false;
	}
	
	boolean extensionPoint() {
		return true;
	}

	boolean hasAccessToActivity(Activity currentActivity, Activity[] allowed) {
		for (Activity activity: allowed) {
			if (activity.equals(currentActivity)) {
				return true;
			}
		}
		return false;
	}

	boolean hasRole(Authentication authentication, Role role) {
		SimpleGrantedAuthority testAuth = new SimpleGrantedAuthority("ROLE_" + role.toString());
		boolean contains = authentication.getAuthorities().contains(testAuth);
		return contains;
	}


}
