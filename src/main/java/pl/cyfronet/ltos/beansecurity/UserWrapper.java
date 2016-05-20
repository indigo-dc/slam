package pl.cyfronet.ltos.beansecurity;

import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.security.permission.OwnedResource;

public class UserWrapper implements OwnedResource {

	private User user;

	public UserWrapper(User user) {
		this.user = user;
	}
	
	@Override
	public String getOwnerId() {
		return user.getLogin();
	}

}
