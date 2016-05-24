package pl.cyfronet.ltos.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import pl.cyfronet.ltos.bean.Affiliation;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.permission.Activity;
import pl.cyfronet.ltos.permission.Permissions;
import pl.cyfronet.ltos.security.bean.AffiliationSecurity;
import pl.cyfronet.ltos.security.bean.UserSecurity;

public class SecurityExpressionRoot extends MethodSecurityExpressionRoot {

	private static Logger logger = LoggerFactory.getLogger(SecurityExpressionRoot.class);
	private Permissions permissions;
	
	public SecurityExpressionRoot(Authentication authentication, Permissions f) {
		super(authentication);
		this.permissions = f;
	}

	public boolean checkPolicy(Activity activity) {
		logger.debug("authorize collection access: " + activity);
		return permissions.securityPolicy(authentication, null, activity).evaluate();
    }
	
	public boolean checkPolicyUser(User targetObject, Activity activity) {
		logger.debug("authorize user acces: " + activity);
		if (targetObject == null) {
			// allow spring to return 404 instead of 403
			return true;
		} else {
			return permissions.securityPolicy(authentication, new UserSecurity(targetObject), activity).evaluate();
		}
	}
	
	public boolean checkPolicyAffiliation(Affiliation targetObject, Activity activity) {
		logger.debug("authorize affiliation access: " + activity);
		if (targetObject == null) {
			// allow spring to return 404 instead of 403
			return true;
		} else {
			return permissions.securityPolicy(authentication, new AffiliationSecurity(targetObject), activity).evaluate();
		}
	}
	
}
