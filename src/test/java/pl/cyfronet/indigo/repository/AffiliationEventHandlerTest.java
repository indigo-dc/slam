package pl.cyfronet.indigo.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.cyfronet.indigo.bean.Affiliation;
import pl.cyfronet.indigo.repository.handler.AffiliationEventHandler;

import java.util.Date;

/**
 * Created by piotr on 14.07.16.
 */
public class AffiliationEventHandlerTest {

    AffiliationEventHandler handler;
    Affiliation affiliation;

    @Before
    public void setup() {
        handler = new AffiliationEventHandler();
        affiliation = new Affiliation();
        Date before = new Date();
        affiliation.setLastUpdateDate(before);
    }

    @Test
    public void testSave() throws Exception {
        Date before = affiliation.getLastUpdateDate();
        handler.handleSave(affiliation);
        Assert.assertNotSame(before, affiliation.getLastUpdateDate());
    }

    @Test
    public void testCreate() throws Exception {
        Date before = affiliation.getLastUpdateDate();
        handler.handleCreate(affiliation);
        Assert.assertNotSame(before, affiliation.getLastUpdateDate());
        Assert.assertEquals("PENDING", affiliation.getStatus());
    }

}
