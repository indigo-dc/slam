package pl.cyfronet.indigo.security.bean;

import lombok.AllArgsConstructor;
import lombok.ToString;
import pl.cyfronet.indigo.bean.User;
import pl.cyfronet.indigo.security.policy.OwnedResource;

/**
 * @author bwilk
 *
 */
@AllArgsConstructor
@ToString
public class UserSecurity implements OwnedResource {

    private User user;

    @Override
    public Long getOwnerId() {
        return user.getId();
    }

}
