package pl.cyfronet.indigo.security.policy;

/**
 * @author bwilk
 *
 */
public enum Activity {

    // Generic activities
    LIST, SAVE, VIEW,

    // Activities on user resource
    LIST_USERS, SAVE_USER, VIEW_USER,

    // Activities on affiliation resource
    LIST_AFFILIATIONS, SAVE_AFFILIATION, VIEW_AFFILIATION,

}