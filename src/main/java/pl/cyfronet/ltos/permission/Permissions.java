package pl.cyfronet.ltos.permission;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class Permissions {

	@Resource(name = "userPermissions")
	private Collection<Activity> userPermissions;

	public SecurityPolicy securityPolicy(Authentication authentication,
			OwnedResource targetObject, Activity activity) {
		OwnedResourceSecurityPolicy policy = new OwnedResourceSecurityPolicy();
		policy.setAuthentication(authentication);
		policy.setTargetObject(targetObject);
		policy.setActivity(activity);
		policy.setUserPermissions(userPermissions);
		return policy;
	}

}
