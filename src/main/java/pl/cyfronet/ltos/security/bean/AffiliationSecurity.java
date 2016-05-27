package pl.cyfronet.ltos.security.bean;

import lombok.AllArgsConstructor;
import lombok.ToString;
import pl.cyfronet.ltos.bean.Affiliation;
import pl.cyfronet.ltos.security.policy.OwnedResource;

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
		// TODO - CAUTION: object must be attached to JPA session   
		return affiliation.getOwner().getId();
	}

}