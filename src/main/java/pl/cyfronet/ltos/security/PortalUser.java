package pl.cyfronet.ltos.security;

import org.springframework.security.core.Authentication;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.security.policy.Identity;

public interface PortalUser extends Authentication, Identity {
    default User getUserBean() {
        return (User) getDetails();
    }
}
