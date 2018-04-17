package pl.cyfronet.indigo.repository.domain;

import org.springframework.beans.factory.annotation.Autowired;

import pl.cyfronet.indigo.bean.Affiliation;
import pl.cyfronet.indigo.repository.AbstractDataOnDemand;

public class AffiliationDataOnDemand extends AbstractDataOnDemand<Affiliation> {

	@Autowired
	UserDataOnDemand userDataOnDemand;
	
    @Override
    protected int getExpectedElements() {
        return 2;
    }

    @Override
    public Affiliation getNewTransientObject(int i) {
    	Affiliation affiliation = new Affiliation();
    	affiliation.setOwner(userDataOnDemand.getRandomObject());
    	return affiliation;
    }

}
