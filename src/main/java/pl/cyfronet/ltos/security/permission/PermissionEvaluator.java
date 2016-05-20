package pl.cyfronet.ltos.security.permission;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public abstract class PermissionEvaluator<T> {
	
	abstract boolean hasPermission(Authentication authentication, T targetObject, Activity activity); 
	
	protected boolean allowedActivity(Activity currentActivity, Activity[] allowed) {
		for (Activity activity: allowed) {
			if (activity.equals(currentActivity)) {
				return true;
			}
		}
		return false;
	}

	protected boolean hasRole(Authentication authentication, Role role) {
		SimpleGrantedAuthority testAuth = new SimpleGrantedAuthority("ROLE_" + role.toString());
		boolean contains = authentication.getAuthorities().contains(testAuth);
		return contains;
	}

}
