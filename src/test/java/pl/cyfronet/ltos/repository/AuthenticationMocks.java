package pl.cyfronet.ltos.repository;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;

import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.bean.UserAuth;
import pl.cyfronet.ltos.security.PortalUser;

public class AuthenticationMocks {

	private AuthenticationMocks() {
	}

	public static Authentication userAuthentication(Long id) {
		User user = User.builder().id(id).build();
		UserAuth auth = UserAuth.builder().login("user1").password("userpass").admin(false).user(user).build();		
		return new TestingAuthenticationToken(new PortalUser(auth), null, "ROLE_USER");
	}

	public static Authentication adminAuthentication(Long id) {
		User user = User.builder().id(id).build();
		UserAuth auth = UserAuth.builder().login("admin").password("adminpass").admin(true).user(user).build();
		return new TestingAuthenticationToken(new PortalUser(auth), null,  "ROLE_USER", "ROLE_ADMIN");
	}
	
}
