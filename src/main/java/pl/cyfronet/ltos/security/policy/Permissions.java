package pl.cyfronet.ltos.security.policy;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class Permissions {

	@Resource(name = "userPermissions")
	private Collection<Activity> userPermissions;

	public SecurityPolicy securityPolicy(Identity identity, OwnedResource targetObject, Activity activity) {
		OwnedResourceSecurityPolicy policy = new OwnedResourceSecurityPolicy();
		policy.setIdentity(identity);
		policy.setTargetObject(targetObject);
		policy.setActivity(activity);
		policy.setUserPermissions(userPermissions);
		return policy;
	}

}
