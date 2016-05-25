package pl.cyfronet.ltos.security.bean;

import lombok.AllArgsConstructor;
import lombok.ToString;
import pl.cyfronet.ltos.bean.Affiliation;
import pl.cyfronet.ltos.security.policy.OwnedResource;

@AllArgsConstructor
@ToString
public class AffiliationSecurity implements OwnedResource {
	
	private Affiliation affiliation;
	
	@Override
	public Long getOwnerId() {
		// fix this - user should be taken from database by id  
		return affiliation.getOwner().getId();
	}

}