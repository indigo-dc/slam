package pl.cyfronet.ltos.security;

import pl.cyfronet.ltos.security.permission.Activity;

public class Activities {
	
	public Activity get(String name) {
		return Activity.valueOf(name);
	}

}
