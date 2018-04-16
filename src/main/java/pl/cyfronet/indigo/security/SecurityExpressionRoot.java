package pl.cyfronet.indigo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import pl.cyfronet.indigo.bean.Affiliation;
import pl.cyfronet.indigo.bean.User;
import pl.cyfronet.indigo.security.bean.AffiliationSecurity;
import pl.cyfronet.indigo.security.bean.UserSecurity;
import pl.cyfronet.indigo.security.policy.Activity;
import pl.cyfronet.indigo.security.policy.Identity;
import pl.cyfronet.indigo.security.policy.Permissions;

/**
 * @author bwilk
 *
 */
/*
 * Consider going back to standard hasPermission calls with instance type checks
 * instead of implementing custom checkPolicy methods
 */
public class SecurityExpressionRoot extends MethodSecurityExpressionRoot {

    private static Logger logger = LoggerFactory
            .getLogger(SecurityExpressionRoot.class);
    private Permissions permissions;
    private Identity identity;

    public SecurityExpressionRoot(Authentication authentication, Permissions f) {
        super(authentication);
        this.identity = (Identity)authentication;
        this.permissions = f;
    }

    public boolean checkPolicy(Activity activity) {
        logger.debug("authorize collection access: " + activity);
        return permissions.securityPolicy(identity, null, activity).evaluate();
    }

    public boolean checkPolicyUser(User targetObject, Activity activity) {
        logger.debug("authorize user acces: " + activity);
        if (targetObject == null) {
            // allow spring to return 404 instead of 403
            return true;
        } else {
            return permissions.securityPolicy(identity,
                    new UserSecurity(targetObject), activity).evaluate();
        }
    }

    public boolean checkPolicyAffiliation(Affiliation targetObject,
            Activity activity) {
        logger.debug("authorize affiliation access: " + activity);
        if (targetObject == null) {
            // allow spring to return 404 instead of 403
            return true;
        } else {
            return permissions.securityPolicy(identity,
                    new AffiliationSecurity(targetObject), activity).evaluate();
        }
    }

}
