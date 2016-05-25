package pl.cyfronet.ltos.security.policy;

import java.util.Collection;

import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class RoleBasedSecurityPolicy<T> extends AbstractSecurityPolicy<T> {
	
	static Logger logger = LoggerFactory.getLogger(RoleBasedSecurityPolicy.class);
	
	@Setter
	private Collection<Activity> userPermissions;
	
	@Override
	public boolean evaluate() {
		logger.debug("evaluate: " 
				+ (targetObject != null ? targetObject.toString() : null)
				+ ", activity: " + activity + ", user: " + identity);
		if (hasRole(identity, Role.ADMIN)) {
			return true;
		} 
		if (hasRole(identity, Role.USER)) {			
			if (hasAccessToActivity(activity, userPermissions)) {
				if (targetObject == null) {
					// TODO it should be chain of evaluate method instead 
					return isAuthorizedOnCollection();
				} else {
					// TODO it should be chain of evaluate method instead 
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

	static boolean hasRole(Identity identity, Role role) {
		SimpleGrantedAuthority testAuth = new SimpleGrantedAuthority("ROLE_" + role.toString());
		boolean contains = identity.getAuthorities().contains(testAuth);
		return contains;
	}

}
