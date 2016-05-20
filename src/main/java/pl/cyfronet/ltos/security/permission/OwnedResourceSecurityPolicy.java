package pl.cyfronet.ltos.security.permission;

import lombok.NonNull;

import org.springframework.security.core.Authentication;

import pl.cyfronet.ltos.beansecurity.OwnedResource;

public class OwnedResourceSecurityPolicy extends PermissionEvaluator<OwnedResource> {

	@Override
	public boolean hasPermission(Authentication identity, @NonNull OwnedResource targetObject, Activity activity) {
		if (hasRole(identity, Role.ADMIN)) {
			return true;
		} 
		if (hasRole(identity, Role.USER)) {			
			Activity[] allowed = { Activity.VIEW_USER, Activity.SAVE_USER };
			if ( allowedActivity(activity, allowed) && targetObject.getOwnerName().equals(identity.getName())) {
				return true;
			}
		}
		return false;
	}
	
}
