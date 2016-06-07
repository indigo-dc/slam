package pl.cyfronet.ltos.security.policy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bwilk
 *
 */
public class OwnerBasedSecurityPolicy extends
        AbstractSecurityPolicy<OwnedResource> implements SecurityPolicy {

    static Logger logger = LoggerFactory
            .getLogger(OwnerBasedSecurityPolicy.class);

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
        return targetObject == null ? true : hasOwnership(this.identity,
                this.targetObject);
    }

    static boolean hasOwnership(Identity identity, OwnedResource targetObject) {
        try {
            return identity.getId().equals(targetObject.getOwnerId());
        } catch (NullPointerException e) {
            // handled here just for convenience of getOwnerId implementation
            return false;
        }
    }

}
