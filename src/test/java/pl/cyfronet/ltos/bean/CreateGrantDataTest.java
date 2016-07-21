package pl.cyfronet.ltos.bean;

import org.junit.Assert;
import org.junit.Test;
import pl.cyfronet.ltos.bean.legacy.CreateGrantData;

/**
 * Created by piotr on 13.07.16.
 */

public class CreateGrantDataTest {

    @Test
    public void testGrantData() throws Exception {
        CreateGrantData grantData = new CreateGrantData();
        grantData.setGrantId("1");
        grantData.setTeam("test");
        grantData.setAffiliationId(1);
        grantData.setBranchOfScienceId("test");

        Assert.assertEquals("1", grantData.getGrantId());
        Assert.assertEquals("test", grantData.getTeam());
        Assert.assertEquals(new Integer(1), grantData.getAffiliationId());
        Assert.assertEquals("test", grantData.getBranchOfScienceId());
    }

}
