package pl.cyfronet.indigo.security.bean;

import lombok.AllArgsConstructor;
import lombok.ToString;
import pl.cyfronet.indigo.bean.Affiliation;
import pl.cyfronet.indigo.security.policy.OwnedResource;

/**
 * @author bwilk
 *
 */
@AllArgsConstructor
@ToString
public class AffiliationSecurity implements OwnedResource {

    private Affiliation affiliation;

    @Override
    public Long getOwnerId() {
        /*
         *  TODO - CAUTION: object must be attached to JPA session
         *  in order to lazy load dependency
         */
        return affiliation.getOwner().getId();
    }

}