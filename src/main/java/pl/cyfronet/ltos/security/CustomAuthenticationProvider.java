package pl.cyfronet.ltos.security;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.security.OurUser.OurUserBuilder;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    static Logger log = LoggerFactory
            .getLogger(CustomAuthenticationProvider.class);

    @Autowired
    UserOperations operations;

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
            OurUserBuilder builder = OurUser.builder();
            builder.credentials(authentication.getCredentials());
            return shouldAuthenticateAgainstThirdPartySystem(name, password, builder);
        } else if (authentication.getClass().isAssignableFrom(TestingAuthenticationToken.class)) {
            return authentication;
        }
        return null;
    }

    private OurUser shouldAuthenticateAgainstThirdPartySystem(String name,
            String password, OurUserBuilder builder) {
        if (name.equals("admin") && password.equals("admin")) {
            User user = operations.loadUserByUnityIdentity("admin");
            if (user == null) {
                operations.createAdmin();
                user = operations.loadUserByUnityIdentity("admin");
            }
            builder.details(user);
            builder.principal(user);
            builder.authorities(Arrays.asList(
                    new SimpleGrantedAuthority("ROLE_ADMIN"), 
                    new SimpleGrantedAuthority("ROLE_USER")));
            builder.isAuthenticated(true);            
            return builder.build();
        }
        if (name.equals("user") && password.equals("user")) {
            User user = operations.loadUserByUnityIdentity("user");
            if (user == null) {
                operations.createUser();
                user = operations.loadUserByUnityIdentity("user");
            }
            builder.details(user);
            builder.principal(user);
            builder.authorities(Arrays.asList(new SimpleGrantedAuthority(
                    "ROLE_USER")));
            builder.isAuthenticated(true);
            return builder.build();
        }
        return null;
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
        public User createAdmin() {
            User user = User.builder().unityPersistentIdentity("admin")
                    .name("Adam").surname("Adminowski")
                    .email("adam@adminowski.pl").build();
            em.persist(user);
            em.flush();
            return user;
        }

        @Transactional
        public User createUser() {
            User user = User.builder().unityPersistentIdentity("user")
                    .name("Ukasz").surname("Userowski")
                    .email("ukasz@userowski.pl").build();
            em.persist(user);
            em.flush();
            return user;
        }

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
        
    }

}