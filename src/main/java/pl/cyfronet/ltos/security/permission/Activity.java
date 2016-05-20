package pl.cyfronet.ltos.security.permission;

public enum Activity {

	// Generic activities
	LIST,

	// Activities on user resource
	LIST_USERS, SAVE_USER, VIEW_USER,
	
	// Activities on affiliation resource 
	LIST_AFFILIATIONS, SAVE_AFFILIATION, VIEW_AFFILIATION,

}