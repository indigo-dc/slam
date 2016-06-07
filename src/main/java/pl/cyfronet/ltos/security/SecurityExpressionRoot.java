package pl.cyfronet.ltos.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import pl.cyfronet.ltos.bean.Affiliation;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.security.bean.AffiliationSecurity;
import pl.cyfronet.ltos.security.bean.UserSecurity;
import pl.cyfronet.ltos.security.policy.Activity;
import pl.cyfronet.ltos.security.policy.Identity;
import pl.cyfronet.ltos.security.policy.Permissions;

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
    private Identity user;

    public SecurityExpressionRoot(Authentication authentication, Permissions f) {
        super(authentication);
        this.user = (Identity) authentication.getPrincipal();
        this.permissions = f;
    }

    public boolean checkPolicy(Activity activity) {
        logger.debug("authorize collection access: " + activity);
        return permissions.securityPolicy(user, null, activity).evaluate();
    }

    public boolean checkPolicyUser(User targetObject, Activity activity) {
        logger.debug("authorize user acces: " + activity);
        if (targetObject == null) {
            // allow spring to return 404 instead of 403
            return true;
        } else {
            return permissions.securityPolicy(user,
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
            return permissions.securityPolicy(user,
                    new AffiliationSecurity(targetObject), activity).evaluate();
        }
    }

}
