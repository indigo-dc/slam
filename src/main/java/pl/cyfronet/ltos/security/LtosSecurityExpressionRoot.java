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
import pl.cyfronet.ltos.security.permission.SecurityPolicy;

public class LtosSecurityExpressionRoot extends MethodSecurityExpressionRoot {

	private static Logger logger = LoggerFactory.getLogger(LtosSecurityExpressionRoot.class);
	
	public LtosSecurityExpressionRoot(Authentication authentication) {
		super(authentication);
	}

	public boolean checkPolicy(Activity activity) {
		logger.info("authorize many: " + activity);
		return true;
    }
	
	public boolean checkPolicy(User targetObject, Activity activity) {
		logger.info("authorize access to user: " + activity);
		SecurityPolicy policy = new OwnedResourceSecurityPolicy(authentication,
				new UserWrapper(targetObject), activity);
		return policy.evaluate();
	}
	
	public boolean checkPolicy(Affiliation targetObject, Activity activity) {
		SecurityPolicy policy = new OwnedResourceSecurityPolicy(authentication,
				new AffiliationWrapper(targetObject), activity);
		return policy.evaluate();
	}
	
	
	
}
