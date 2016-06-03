package pl.cyfronet.ltos.security;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import pl.cyfronet.ltos.bean.UserAuth;
import pl.cyfronet.ltos.bean.User;

@Component
@Repository
public class UserDetailsRepo {

	@PersistenceContext
	EntityManager em;

	@Transactional
	private PortalUser admin() {
		UserAuth pu = em.find(UserAuth.class, "admin");
		if (pu == null) {
			User user = User.builder().name("Adam").surname("Adminowski")
					.email("adam@adminowski.pl").build();
			em.persist(user);
			pu = UserAuth.builder().login("admin").password("admin")
					.admin(true).user(user).build();
			em.persist(pu);
			em.flush();
		}
		return new PortalUser(pu);
	}
	//
	// @Transactional
	// private PortalUserWrapper user() {
	// PortalUser pu = em.find(PortalUser.class, "user");
	// if (pu == null) {
	// User user =
	// User.builder().name("Ukasz").surname("Userowski").email("ukasz@userowski.pl").build();
	// em.persist(user);
	// pu =
	// PortalUser.builder().login("user").password("user").admin(false).user(user).build();
	// em.persist(pu);
	// em.flush();
	// }
	// return new PortalUserWrapper(pu);
	// }

}