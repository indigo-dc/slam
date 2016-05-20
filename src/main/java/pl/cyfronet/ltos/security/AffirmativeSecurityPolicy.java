package pl.cyfronet.ltos.security;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import pl.cyfronet.ltos.security.permission.Activity;

public class AffirmativeSecurityPolicy implements PermissionEvaluator {
	
	private static Logger logger = LoggerFactory.getLogger(RoleBasedSecurityPolicy.class);

	@Override
	public boolean hasPermission(Authentication authentication,
			Object targetDomainObject, Object permission) {
		logger.info("hasPermission || " + authentication + " || " + targetDomainObject + " || " + permission);
		if (permission instanceof Activity) {
			Activity activity = (Activity) permission;
			return authorizedActivity(authentication, targetDomainObject, activity);
		}
		return false;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		logger.info("hasPermission || " + authentication + " || " + targetId + " || " +  targetType + " || " +  permission);
		return false;
	}
	
	protected boolean authorizedActivity(Authentication authentication,
			Object targetDomainObject, Activity activity) {
		return true;
	}

}
