package pl.cyfronet.ltos.security.permission;

import org.springframework.security.core.Authentication;

public class OwnedResourceSecurityPolicy extends DefaultSecurityPolicy<OwnedResource> {

	public OwnedResourceSecurityPolicy(Authentication identity, Activity activity) {
		this(identity, null, activity);
	}
	
	public OwnedResourceSecurityPolicy(Authentication identity, OwnedResource targetObject, Activity activity) {
		super(identity, targetObject, activity);
	}
	
	@Override
	boolean extensionPoint() {
		return hasOwnership(identity, targetObject);
	}
	
	boolean hasOwnership(Authentication identity, OwnedResource targetObject) {
		return identity.getName().equals(targetObject.getOwnerId());
	}
	
}
