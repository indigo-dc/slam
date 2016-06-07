package pl.cyfronet.ltos.security;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.bean.UserAuth;

/**
 * @author bwilk
 *
 */
@Configuration
@Profile("development")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigDevNew extends SecurityConfig {

	@Autowired
	private UserDetailsRepo repo;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(username -> {
			switch (username) {
			case "admin":
				return repo.admin();
			case "user":
				return repo.user();
			default:
				throw new UsernameNotFoundException("Username " + username
						+ " not found.");
			}
		});
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/bootstrap-3.3.1-dist/**", "/static/**","/resources/**", "/bootstrap/**",
                        "/css/**", "/images/**", "/js/**", "/javascript/**", "/fonts/**")
                .permitAll()
			.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.and()
				.csrf()
					.disable();
		
	}
	

	@Component
	@Repository
	public static class UserDetailsRepo {
	
		@PersistenceContext
		EntityManager em;
		
		@Transactional
		public PortalUser admin() {
			UserAuth pu = em.find(UserAuth.class, "admin");
			if (pu == null) {
				User user = User.builder().name("Adam").surname("Adminowski").email("adam@adminowski.pl").build();
				em.persist(user);
				pu = UserAuth.builder().login("admin").password("admin").admin(true).build();
				pu.setUser(user);
				em.persist(pu);
				em.flush();
			}
			return new PortalUser(pu);
		}
		
		@Transactional
		public PortalUser user() {
			UserAuth pu = em.find(UserAuth.class, "user");
			if (pu == null) {
				User user = User.builder().name("Ukasz").surname("Userowski").email("ukasz@userowski.pl").build();
				em.persist(user);
				pu = UserAuth.builder().login("user").password("user").admin(false).build();
				pu.setUser(user);
				em.persist(pu);
				em.flush();
			}
			return new PortalUser(pu);
		}

	}
	
}