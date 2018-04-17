package pl.cyfronet.indigo.security;

import org.springframework.security.core.Authentication;
import pl.cyfronet.indigo.bean.User;
import pl.cyfronet.indigo.security.policy.Identity;

public interface PortalUser extends Authentication, Identity {
    default User getUserBean() {
        return (User) getDetails();
    }
}
