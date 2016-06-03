package pl.cyfronet.ltos.repository;

import java.util.Arrays;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.bean.UserAuth;
import pl.cyfronet.ltos.security.PortalUser;
import pl.cyfronet.ltos.security.SimpleUser;

public class AuthenticationMocks {

	private AuthenticationMocks() {
	}

	public static Authentication userAuthentication(Long id) {
		User user = User.builder().id(id).build();
		UserAuth auth = UserAuth.builder().login("user1").password("userpass").admin(false).user(user).build();		
		return new TestingAuthenticationToken(new SimpleUser(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")), "admin", "adminpass", id), null, "ROLE_USER");
	}

	public static Authentication adminAuthentication(Long id) {
		//User user = User.builder().id(id).build();
		//UserAuth auth = UserAuth.builder().login("admin").password("adminpass").admin(true).user(user).build();
		return new TestingAuthenticationToken(new SimpleUser(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")), "admin", "adminpass", id), null,  "ROLE_USER", "ROLE_ADMIN");
	}
	
}
