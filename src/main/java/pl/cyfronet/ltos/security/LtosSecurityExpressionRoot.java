package pl.cyfronet.ltos.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import pl.cyfronet.ltos.beansecurity.OwnedResource;
import pl.cyfronet.ltos.security.permission.Activity;
import pl.cyfronet.ltos.security.permission.OwnedResourceSecurityPolicy;

public class LtosSecurityExpressionRoot extends MethodSecurityExpressionRoot {

	private static Logger logger = LoggerFactory.getLogger(LtosSecurityExpressionRoot.class);
	
	private OwnedResourceSecurityPolicy ownedResEval =  new OwnedResourceSecurityPolicy();
	
	public LtosSecurityExpressionRoot(Authentication authentication) {
		super(authentication);
	}

	public boolean checkPolicy(Activity activity) {
		logger.info("authorize many: " + activity);
		return true;
    }
	
	public boolean checkPolicy(OwnedResource targetObject, Activity activity) {
		logger.info("authorize owned resource: " + targetObject + " | " + activity);
		boolean hasPermission;
		try {	
			hasPermission = ownedResEval.hasPermission(authentication, targetObject, activity);
		} catch (Exception e) {
			return false; 
		}
		return hasPermission;
	}

}
