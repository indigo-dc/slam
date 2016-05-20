package pl.cyfronet.ltos.security.bean;

import lombok.AllArgsConstructor;
import lombok.ToString;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.permission.OwnedResource;

@AllArgsConstructor
@ToString
public class UserSecurity implements OwnedResource {

	private User user;

	@Override
	public String getOwnerId() {
		return user.getLogin();
	}

}
