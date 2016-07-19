package pl.cyfronet.ltos.security;

import org.junit.Assert;
import org.junit.Test;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.security.bean.UserSecurity;

/**
 * Created by piotr on 15.07.16.
 */
public class UserSecurityTest {

    @Test
    public void testUserSecurity() throws Exception {
        User user = User.builder().id(500L).build();
        UserSecurity userSecurity = new UserSecurity(user);

        Assert.assertEquals(new Long(500), userSecurity.getOwnerId());
    }

}
