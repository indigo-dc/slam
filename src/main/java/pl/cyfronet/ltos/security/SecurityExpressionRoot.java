package pl.cyfronet.ltos.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import pl.cyfronet.ltos.bean.Affiliation;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.beansecurity.AffiliationWrapper;
import pl.cyfronet.ltos.beansecurity.UserWrapper;
import pl.cyfronet.ltos.security.permission.Activity;
import pl.cyfronet.ltos.security.permission.OwnedResourceSecurityPolicy;

public class SecurityExpressionRoot extends MethodSecurityExpressionRoot {

	private static Logger logger = LoggerFactory.getLogger(SecurityExpressionRoot.class);
	
	public SecurityExpressionRoot(Authentication authentication) {
		super(authentication);
	}

	public boolean checkPolicy(Activity activity) {
		logger.info("authorize many: " + activity);
		return new OwnedResourceSecurityPolicy(authentication, activity).evaluate();
    }
	
	public boolean checkPolicyUser(User targetObject, Activity activity) {
		logger.info("authorize access to user: " + activity);
		if (targetObject == null) {
			// allow spring to return 404 instead of 403
			return true;
		}
		return new OwnedResourceSecurityPolicy(authentication, new UserWrapper(targetObject), activity).evaluate();
	}
	
	public boolean checkPolicyAffiliation(Affiliation targetObject, Activity activity) {
		logger.info("authorize access to affiliation: " + activity);
		if (targetObject == null) {
			// allow spring to return 404 instead of 403
			return true;
		}
		return new OwnedResourceSecurityPolicy(authentication, new AffiliationWrapper(targetObject), activity).evaluate();
	}
	
}
