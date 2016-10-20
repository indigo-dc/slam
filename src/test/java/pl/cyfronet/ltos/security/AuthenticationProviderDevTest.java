package pl.cyfronet.ltos.security;

import com.agreemount.bean.identity.Identity;
import org.junit.Assert;
import org.junit.Test;
import pl.cyfronet.ltos.bean.Role;
import pl.cyfronet.ltos.bean.Team;
import pl.cyfronet.ltos.bean.User;

import java.util.Arrays;

/**
 * Created by piotr on 14.07.16.
 */

public class AuthenticationProviderDevTest {

    @Test
    public void testGetIdentity() throws Exception {
        AuthenticationProviderDev authentication = new AuthenticationProviderDev();
        User user = new User();
        user.setId(1L);
        Team team = new Team();
        team.setName("test");
        user.setTeams(Arrays.asList(team));
        Role role = new Role();
        role.setName("test");
        team.setRoles(Arrays.asList(role));
        user.setRoles(Arrays.asList(new Role()));
        Identity identity = authentication.getIdentity(user);

        Assert.assertNotNull(identity);
//        Assert.assertNotNull(identity.getLogin());
    }

}
