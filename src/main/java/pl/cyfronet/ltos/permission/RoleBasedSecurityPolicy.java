package pl.cyfronet.ltos.permission;

import java.util.Collection;

import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class RoleBasedSecurityPolicy<T> extends AbstractSecurityPolicy<T> {
	
	static Logger logger = LoggerFactory.getLogger(RoleBasedSecurityPolicy.class);
	
	@Setter
	private Collection<Activity> userPermissions;
	
	@Override
	public boolean evaluate() {
		logger.debug("evaluate: " 
				+ (targetObject != null ? targetObject.toString() : null)
				+ ", activity: " + activity + ", user: " + authentication);
		if (hasRole(authentication, Role.ADMIN)) {
			return true;
		} 
		if (hasRole(authentication, Role.USER)) {			
			if (hasAccessToActivity(activity, userPermissions)) {
				if (targetObject == null) {
					return isAuthorizedOnCollection();
				} else {
					return isAuthorizedOnTargetObject();	
				}
			}
		}
		return false;
	}

	protected boolean isAuthorizedOnCollection() {
		return true;
	}

	protected boolean isAuthorizedOnTargetObject() {
		return true;
	}

	static boolean hasAccessToActivity(Activity currentActivity, Collection<Activity> allowed) {		
		for (Activity activity: allowed) {
			if (activity.equals(currentActivity)) {
				return true;
			}
		}
		return false;
	}

	static boolean hasRole(Authentication authentication, Role role) {
		SimpleGrantedAuthority testAuth = new SimpleGrantedAuthority("ROLE_" + role.toString());
		boolean contains = authentication.getAuthorities().contains(testAuth);
		return contains;
	}

}
