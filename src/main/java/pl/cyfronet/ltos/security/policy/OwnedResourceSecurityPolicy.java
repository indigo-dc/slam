package pl.cyfronet.ltos.security.policy;


public class OwnedResourceSecurityPolicy extends RoleBasedSecurityPolicy<OwnedResource> {
	
	@Override
	protected boolean isAuthorizedOnTargetObject() {
		return hasOwnership(identity, targetObject);
	}
	
	static boolean hasOwnership(Identity identity, OwnedResource targetObject) {
		try {
			return identity.getId().equals(targetObject.getOwnerId());
		} catch (NullPointerException e) {
			// handled here just for convenience of getOwnerId implementation 
			return false;
		}
	}
	
}
