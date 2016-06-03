package pl.cyfronet.ltos.security;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.cyfronet.ltos.bean.User;

/**
 * @author bwilk
 *
 */
@Configuration
@Profile("development2")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigDev extends SecurityConfig {

	@Autowired
	private DevUserRepo repo;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(username -> {
			switch (username) {
			case "admin":
				return repo.createInDb(
						new SimpleUser(Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")), "admin", "admin", 1L),
						"Adam", "Adminowski", "adam@adminowski.pl");
			case "user":
				return repo.createInDb(
						new SimpleUser(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")), "user", "user", 2L),
						"Ukasz", "Userowski", "ukasz@userowski.pl");
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
	@Scope("singleton")
	@Repository
	public static class DevUserRepo {
		
		@PersistenceContext
		private EntityManager em;

		@Transactional
		public SimpleUser createInDb(SimpleUser pu, String name,
				String surname, String email) {
			User user = null;
			if (pu.getUserBean() == null) {
				user = em.find(User.class, pu.getUserBeanId());
				if (user == null) {
					user = User.builder().name(name).surname(surname).email(email).build();
					em.persist(user);
					em.flush();
				}
				pu.setUserBean(user);
				pu.setUserBeanId(user.getId());
			}
			return pu;
		}

	}

}