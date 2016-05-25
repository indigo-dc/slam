package pl.cyfronet.ltos.security.policy;

public enum Activity {

	// Generic activities
	ALL, LIST, SAVE, VIEW,

	// Activities on user resource
	LIST_USERS, SAVE_USER, VIEW_USER,
	
	// Activities on affiliation resource 
	LIST_AFFILIATIONS, SAVE_AFFILIATION, VIEW_AFFILIATION,

}