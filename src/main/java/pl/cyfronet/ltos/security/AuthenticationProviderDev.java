package pl.cyfronet.ltos.security;

import com.agreemount.bean.identity.Identity;
import com.agreemount.bean.identity.TeamMember;
import com.agreemount.bean.identity.provider.IdentityProvider;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.cyfronet.ltos.bean.Role;
import pl.cyfronet.ltos.bean.Team;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.repository.TeamRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AuthenticationProviderDev implements AuthenticationProvider {

    @Autowired
    private UserOperations operations;

    @Autowired
    private IdentityProvider identityProvider;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PortalUserFactory portalUserFactory;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        if (authentication.getClass().isAssignableFrom(UsernamePasswordAuthenticationToken.class)) {
            String name = authentication.getName();
            String password = authentication.getCredentials().toString();
            log.debug("LOGGING: CustomAuthenticationProvider");
            log.debug("LOGGING: name = " + name + ", roles "
                    + authentication.getAuthorities().toString());
            log.debug("LOGGING: details = " + operations);
            log.debug("LOGGING:  creation = " +  operations );
            PortalUserImpl.Data.DataBuilder builder = PortalUserImpl.Data.builder();
            builder.credentials(authentication.getCredentials());
            return shouldAuthenticateAgainstThirdPartySystem(name, password, builder);
        } else if (authentication.getClass().isAssignableFrom(TestingAuthenticationToken.class)) {
            return authentication;
        }
        return null;
    }

    @Transactional
    public Identity getIdentity(User user) {
        Identity identity = new Identity();
        //TODO for now engine user will be in fact organisation of the user
//        identity.setLogin(user.getId().toString());
        identity.setLogin(user.getOrganisationName());
        List<String> roles = user.getRoles().stream().map(entry -> entry.getName()).collect(Collectors.toList());
        List<Team> teams = user.getTeams();
        List<TeamMember> teamMembers = new LinkedList<TeamMember>();
        for (Team team : teams) {
            for (Role role: team.getRoles()) {
                TeamMember teamMember = new TeamMember();
                teamMember.setRole(role.getName());
                teamMember.setTeam(team.getName());
                teamMembers.add(teamMember);
            }
        }
        identity.setRoles(roles);
        identity.setTeamMembers(teamMembers);
        return identity;
    }

    private PortalUser shouldAuthenticateAgainstThirdPartySystem(
            String name,
            String password,
            PortalUserImpl.Data.DataBuilder builder) {
        if (name.equals("admin") && password.equals("admin")) {
            User user = operations.loadUserByUnityIdentity("admin");
            UserInfo info = UserInfo.fromUser(user);

            List<Role> roles = new ArrayList<>();
            Role role = operations.loadOrCreateRoleByName("admin");
            roles.add(role);
            role = operations.loadOrCreateRoleByName("provider");
            roles.add(role);

            if (user == null) {
                info = createAdminInfo();
                user = operations.saveUser(info.toUserPrototype().country("Poland").roles(roles).build());
                info.setId(user.getId());
                Team team = new Team();
                team.setName("Testowy");
                team.setRoles(roles);
                team.setMembers(Arrays.asList(user));
                teamRepository.save(team);
                user.setTeams(Arrays.asList(team));
            }
            builder.user(user);
            builder.principal(info);
            builder.authority(new SimpleGrantedAuthority("ROLE_ADMIN"));
            builder.authority(new SimpleGrantedAuthority("ROLE_PROVIDER"));
            builder.authority(new SimpleGrantedAuthority("ROLE_USER"));
            builder.authenticated(true);

            Identity identity = getIdentity(user);
            Preconditions.checkNotNull(identity, "Identity [%s] was not found", name);
            identityProvider.setIdentity(identity);

            log.debug("LOGGING:  identity for  engine set = " +  identity );

            return portalUserFactory.createPortalUser(builder.build());
        }
        if (name.equals("user") && password.equals("user")) {
            User user = operations.loadUserByUnityIdentity("user");
            UserInfo info = UserInfo.fromUser(user);
            if (user == null) {
                info = createUserInfo();
                Role role = operations.loadOrCreateRoleByName("manager");
                user = operations.saveUser(info.toUserPrototype().country("Poland").roles(Arrays.asList(role)).build());
                info.setId(user.getId());
                Team team = new Team();
                team.setName("Testowy");
                team.setRoles(Arrays.asList(role));
                team.setMembers(Arrays.asList(user));
                teamRepository.save(team);
                user.setTeams(Arrays.asList(team));
            }
            builder.user(user);
            builder.principal(info);
            builder.authorities(Arrays.asList(
                    new SimpleGrantedAuthority("ROLE_USER"),
                    new SimpleGrantedAuthority("ROLE_MANAGER")
            ));
            builder.authenticated(true);

            Identity identity = getIdentity(user);
            Preconditions.checkNotNull(identity, "Identity [%s] was not found", name);
            identityProvider.setIdentity(identity);

            log.debug("LOGGING:  identity for  engine set = " +  identity );

            return portalUserFactory.createPortalUser(builder.build());
        }
        if (name.equals("noreg")) {
            UserInfo info = createNoregInfo();
            builder.principal(info);
            builder.authenticated(true);

            return portalUserFactory.createPortalUser(builder.build());
        }
        return null;
    }

    private UserInfo createAdminInfo() {
        UserInfo info = UserInfo.builder()
            .unityPersistentIdentity("admin")
            .confirmedRegistration(true)
            .name("Adam Adminowski")
            .organisation_name("test_organisation")
            .email("adam@adminowski.pl").build();
        return info;
    }

    private UserInfo createUserInfo() {
        UserInfo info = UserInfo.builder()
            .unityPersistentIdentity("user")
            .confirmedRegistration(true)
            .name("Ukasz Userowski")
            .organisation_name("test_organisation")
            .email("ukasz@userowski.pl").build();
        return info;
    }

    private UserInfo createNoregInfo() {
        UserInfo info = UserInfo.builder()
            .unityPersistentIdentity("user")
            .confirmedRegistration(true)
            .name("Norbert Niezarejestr")
            .organisation_name("test_organisation")
            .email("norbert@niezarejestr.pl").build();
        return info;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class) || authentication.equals(TestingAuthenticationToken.class);
    }

    @Component
    @Repository
    public static class UserOperations {

        @PersistenceContext
        EntityManager em;

        @Transactional
        public User saveUser(User user) {
            em.persist(user);
            em.flush();
            return user;
        }

        @Transactional
        public Role saveRole(Role role) {
            em.persist(role);
            em.flush();
            return role;
        }

        /*
         * Consider new version of spring data rest - such
         * searches are probably implemented by repositories
         */
        @Transactional
        public User loadUserByUnityIdentity(String username)
                throws UsernameNotFoundException {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> q = cb.createQuery(User.class);
            Root<User> user = q.from(User.class);
            q.select(user).where(cb.equal(user.get("unityPersistentIdentity"), username));
            List<User> resultList = em.createQuery(q).getResultList();
            User principal = null;
            if (!resultList.isEmpty()) {
                principal = resultList.get(0);
            }
            return principal;
        }

        @Transactional
        public Role loadOrCreateRoleByName(String roleName)
                throws UsernameNotFoundException {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Role> q = cb.createQuery(Role.class);
            Root<Role> role = q.from(Role.class);
            q.select(role).where(cb.equal(role.get("name"), roleName));
            List<Role> resultList = em.createQuery(q).getResultList();
            Role theRole = null;
            if (!resultList.isEmpty()) {
                theRole = resultList.get(0);
            } else {
                theRole = new Role();
                theRole.setName(roleName);
                em.persist(theRole);
                em.flush();
            }
            return theRole;
        }

    }

}
