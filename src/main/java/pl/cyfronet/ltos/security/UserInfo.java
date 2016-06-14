package pl.cyfronet.ltos.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import pl.cyfronet.ltos.bean.User;
import pl.cyfronet.ltos.security.policy.Identity;

@Data
@Builder
@ToString
public class UserInfo {

    private Long id;
    private String email;
    private String name;
    private Boolean confirmedRegistration;
    private String unityPersistentIdentity;

    public User.UserBuilder toUserPrototype() {
        return User.builder()
                .email(email)
                .name(name)
                .confirmedRegistration(confirmedRegistration)
                .unityPersistentIdentity(unityPersistentIdentity);
    }

    public static UserInfo fromUser(User user) {
        if (user == null) {
            return null;
        }
        return UserInfo.builder()
            .id(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .confirmedRegistration(user.getConfirmedRegistration())
            .unityPersistentIdentity(user.getUnityPersistentIdentity()).build();
    }

}
