package pl.cyfronet.ltos.permission;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionsConfig {

	@Bean(name="userPermissions")
	static List<Activity> getUserPermissions() {
		Activity[] userPermissions = { 
			Activity.VIEW_USER,
			Activity.SAVE_USER, 
			Activity.VIEW_AFFILIATION,
			Activity.SAVE_AFFILIATION,
			Activity.LIST_AFFILIATIONS 
		}; 
		return Arrays.asList(userPermissions);
	}
	
}
