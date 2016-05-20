package pl.cyfronet.ltos.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.beansecurity.OwnedResource;
import pl.cyfronet.ltos.security.permission.OwnedResourceSecurityPolicy;
import pl.cyfronet.ltos.security.permission.Activity;

public class LtosSecurityExpressionRoot extends MethodSecurityExpressionRoot {

	private static Logger logger = LoggerFactory.getLogger(LtosSecurityExpressionRoot.class);
	
	private OwnedResourceSecurityPolicy ownedResEval =  new OwnedResourceSecurityPolicy();
	
	public LtosSecurityExpressionRoot(Authentication authentication) {
		super(authentication);
	}
		
	public boolean canSaveUser(User targetObject) {
		logger.info("save user: " + targetObject);
		return true;
    }
	
	public boolean isAllowed(Object targetObject, Activity activity) {
		logger.info("authorize one: " + targetObject + " | " + activity );
		return true;
    }

	public boolean isAllowed(Activity activity) {
		logger.info("authorize many: " + activity);
		return true;
    }
	
	public boolean checkPermissions(OwnedResource targetObject, Activity activity) {
		logger.info("authorize owned resource: " + targetObject + " | " + activity);
		boolean hasPermission;
		try {	
			hasPermission = getOwnedResourcePermissionEvaluator().hasPermission(
					authentication, targetObject,
					activity);
		} catch (Exception e) {
			return false; 
		}
		return hasPermission;
	}

	private OwnedResourceSecurityPolicy getOwnedResourcePermissionEvaluator() {
		return ownedResEval;
	}
	
}
