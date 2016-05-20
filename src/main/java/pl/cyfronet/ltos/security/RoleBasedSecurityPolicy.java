package pl.cyfronet.ltos.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.security.permission.Activity;

public class RoleBasedSecurityPolicy extends AffirmativeSecurityPolicy {

	@Override
	protected boolean authorizedActivity(Authentication authentication,
			Object targetDomainObject, Activity activity) {
		return happyPath(authentication, targetDomainObject, activity);
	}
	
	boolean happyPath(Authentication authentication, Object targetDomainObject, Activity activity) {
		if (hasRole(authentication, "ADMIN")) {
			return true;
		} 
		if (hasRole(authentication, "USER")) {
			Activity[] allowed = { Activity.VIEW_USER, Activity.SAVE_USER};
			if (targetDomainObject != null) {
				if (allowedForUser(activity, allowed)) {
					if (targetDomainObject instanceof User) {
						User user = (User)targetDomainObject;
						if (user.getName().equals(authentication.getName())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean allowedForUser(Activity currentActivity, Activity[] allowed) {
		for (Activity activity: allowed) {
			if (activity.equals(currentActivity)) {
				return true;
			}
		}
		return false;
	}

	boolean hasRole(Authentication authentication, String role) {
		SimpleGrantedAuthority testAuth = new SimpleGrantedAuthority("ROLE_" + role);
		boolean contains = authentication.getAuthorities().contains(testAuth);
		return contains;
	}
	
}
