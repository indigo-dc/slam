package pl.cyfronet.ltos.permission;

import org.springframework.security.core.Authentication;

public class OwnedResourceSecurityPolicy extends RoleBasedSecurityPolicy<OwnedResource> {
	
	@Override
	protected boolean isAuthorizedOnTargetObject() {
		return hasOwnership(authentication, targetObject);
	}
	
	static boolean hasOwnership(Authentication authentication, OwnedResource targetObject) {
		try {
			return authentication.getName().equals(targetObject.getOwnerId());
		} catch (NullPointerException e) {
			// handled here just for convenience of getOwnerId implementation 
			return false;
		}
	}
	
}
