package pl.cyfronet.indigo.bean;

import org.junit.Assert;
import org.junit.Test;
import pl.cyfronet.indigo.bean.legacy.GenericBean;
import pl.cyfronet.indigo.bean.legacy.Status;

/**
 * Created by piotr on 13.07.16.
 */
public class GenericBeanTest {

    @Test
    public void testGenericBean() throws Exception {
        GenericBean<String> genericBean = new GenericBean<String>();
        genericBean.setMessage("test");
        genericBean.setType("type");
        genericBean.setStatus(Status.SUCCESS);

        Assert.assertEquals("test", genericBean.getMessage());
        Assert.assertEquals("type", genericBean.getType());
        Assert.assertEquals(Status.SUCCESS, genericBean.getStatus());
    }

}
