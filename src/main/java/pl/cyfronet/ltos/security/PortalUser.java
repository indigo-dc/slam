package pl.cyfronet.ltos.security;

import java.util.Collection;
import java.util.LinkedList;

import javax.transaction.Transactional;

import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import pl.cyfronet.ltos.bean.UserAuth;
import pl.cyfronet.ltos.security.policy.Identity;

	public class PortalUser extends User implements Identity {
		
	private static final long serialVersionUID = 1L;

	@Setter
	private UserAuth userAuth;

	public PortalUser(UserAuth details) {
		super(details.getLogin(), details.getPassword(), getAuthorities(details));
		this.userAuth = details;
	}
	
	public static Collection<GrantedAuthority> getAuthorities(UserAuth details) {
		LinkedList<GrantedAuthority> list = new LinkedList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority("ROLE_USER"));
		if (details.isAdmin()) {
			list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} 
		return list;
	}

	@Override
	@Transactional
	public Long getId() {
		return userAuth.getUser().getId();
	}

}