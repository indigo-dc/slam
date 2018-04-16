package pl.cyfronet.indigo.repository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.cyfronet.indigo.bean.Affiliation;
import pl.cyfronet.indigo.bean.Role;
import pl.cyfronet.indigo.bean.Team;
import pl.cyfronet.indigo.bean.User;
import pl.cyfronet.indigo.security.PortalUser;
import pl.cyfronet.indigo.security.PortalUserImpl.Data;
import pl.cyfronet.indigo.security.UserInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        Collection<? extends GrantedAuthority> auth = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));

        return mockPortalUser(Data.builder().principal(UserInfo.fromUser(user)).user(user).authorities(auth).authenticated(true).build());
    }

    public static Authentication adminAuthentication(Long id) {
        User user = User.builder().id(id).build();
        List<GrantedAuthority> auth = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        return mockPortalUser(Data.builder().principal(UserInfo.fromUser(user)).user(user).authorities(auth).authenticated(true).build());
    }

    private static PortalUser mockPortalUser(Data data) {
        PortalUser puMock = mock(PortalUser.class);
        when(puMock.getPrincipal()).thenReturn(UserInfo.fromUser(data.getUser()));
        when(puMock.getUserBean()).thenReturn(data.getUser());
        when(puMock.getDetails()).thenReturn(data.getUser());
        when(puMock.getAuthorities()).thenAnswer(invocation -> data.getAuthorities());
        when(puMock.isAuthenticated()).thenReturn(data.isAuthenticated());
        return puMock;
    }

}
