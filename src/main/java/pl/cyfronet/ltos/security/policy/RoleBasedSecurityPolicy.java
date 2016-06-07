package pl.cyfronet.ltos.security.policy;

import java.util.Collection;

import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bwilk
 *
 */
public class RoleBasedSecurityPolicy<T> extends AbstractSecurityPolicy<T> {

    static Logger logger = LoggerFactory
            .getLogger(RoleBasedSecurityPolicy.class);

    @Setter
    private Collection<Activity> userPermissions;

    @Override
    public boolean evaluate() {
        boolean result = doEvaluate();
        logger.debug("Target: "
                + (targetObject != null ? targetObject.toString() : null)
                + "; Activity: " + activity + ", Identity: " + identity
                + "; Result: " + result);
        return result;
    }

    private boolean doEvaluate() {
        if (isSuperuser(identity)) {
            return true;
        }
        if (hasRole(identity, Role.USER)) {
            if (hasAccessToActivity(activity, userPermissions)) {
                return true;
            }
        }
        return false;
    }

}
