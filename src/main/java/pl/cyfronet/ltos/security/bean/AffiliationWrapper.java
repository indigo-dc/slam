package pl.cyfronet.ltos.security.bean;

import pl.cyfronet.ltos.bean.Affiliation;
import pl.cyfronet.ltos.security.permission.OwnedResource;

public class AffiliationWrapper implements OwnedResource {

	private Affiliation affiliation;

	public AffiliationWrapper(Affiliation affiliation) {
		this.affiliation = affiliation;
	}
	
	@Override
	public String getOwnerId() {
		return affiliation.getOwner().getLogin();
	}

}