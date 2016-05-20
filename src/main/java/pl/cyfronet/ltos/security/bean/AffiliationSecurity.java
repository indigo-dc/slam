package pl.cyfronet.ltos.security.bean;

import lombok.AllArgsConstructor;
import lombok.ToString;
import pl.cyfronet.ltos.bean.Affiliation;
import pl.cyfronet.ltos.permission.OwnedResource;

@AllArgsConstructor
@ToString
public class AffiliationSecurity implements OwnedResource {
	
	private Affiliation affiliation;
	
	@Override
	public String getOwnerId() {
		// fix this - user should be taken from database by id  
		return affiliation.getOwner().getLogin();
	}

}