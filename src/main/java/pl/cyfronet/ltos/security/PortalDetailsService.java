package pl.cyfronet.ltos.security;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import pl.cyfronet.ltos.bean.UserAuth;

/**
 * @author bwilk
 *
 */
public class PortalDetailsService implements UserDetailsService {

    @PersistenceContext
    protected EntityManager em;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserAuth login = em.find(UserAuth.class, username);
        if (login == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new PortalUser(login);
    }

}
