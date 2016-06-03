package pl.cyfronet.ltos.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.security.policy.Identity;

/**
 * @author bwilk
 *
 */
public class SimpleUser extends
		org.springframework.security.core.userdetails.User implements Identity {

	private static final long serialVersionUID = 1L;

	private Long userBeanId;

	private User userBean;

	public SimpleUser(List<GrantedAuthority> authorities, String username,
			String password, Long beanId) {
		super(username, password, authorities);
		this.userBeanId = beanId;
	}

	@Override
	public Long getId() {
		return userBeanId;
	}

	public Long getUserBeanId() {
		return userBeanId;
	}

	public User getUserBean() {
		return userBean;
	}

	public void setUserBean(User user) {
		this.userBean = user;
	}

	public void setUserBeanId(Long userBeanId) {
		this.userBeanId = userBeanId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("; ");
		sb.append("UserBeanId: ").append(this.userBeanId);
		return sb.toString();
	}

}
