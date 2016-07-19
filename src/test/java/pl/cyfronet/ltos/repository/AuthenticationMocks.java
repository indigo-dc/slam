package pl.cyfronet.ltos.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import pl.cyfronet.ltos.bean.Affiliation;
import pl.cyfronet.ltos.bean.Role;
import pl.cyfronet.ltos.bean.Team;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.security.PortalUser;
import pl.cyfronet.ltos.security.UserInfo;

public class AuthenticationMocks {

    private AuthenticationMocks() {
    }

    public static Authentication userAuthentication(Long id) {
        Team team = new Team();
        team.setName("test");
        Affiliation affiliation = new Affiliation();
        affiliation.setStatus("ACTIVE");
        Role role = new Role();
        role.setName("manager");
        User user = User.builder().id(id).name("user").teams(Arrays.asList(team)).affiliations(Arrays.asList(affiliation)).roles(Arrays.asList(role)).build();
        team.setMembers(Arrays.asList(user));
        affiliation.setOwner(user);
        role.setUsers(Arrays.asList(user));
        List<GrantedAuthority> auth = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        PortalUser pu = PortalUser.builder().principal(UserInfo.fromUser(user)).user(user).authorities(auth).isAuthenticated(true).build();
        return pu;
    }

    public static Authentication adminAuthentication(Long id) {
        User user = User.builder().id(id).build();
        List<GrantedAuthority> auth = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        PortalUser pu = PortalUser.builder().principal(UserInfo.fromUser(user)).user(user).authorities(auth).isAuthenticated(true).build();
        return pu;
    }

}
