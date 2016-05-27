package pl.cyfronet.ltos.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import pl.cyfronet.ltos.security.policy.Identity;

public class PortalUser extends User implements Identity {

	private static final long serialVersionUID = 1L;

	private Long userBeanId;

	public PortalUser(List<GrantedAuthority> authorities, String username,
			String password, Long userBeanId) {
		super(username, password, authorities);
		this.userBeanId = userBeanId;
	}
	
	@Override
	public Long getId() {
		return userBeanId;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("; ");
		sb.append("UserBeanId: ").append(this.userBeanId);
		return sb.toString();
	}
	
}
