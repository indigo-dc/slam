package pl.cyfronet.ltos.security;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import pl.cyfronet.ltos.bean.Role;
import pl.cyfronet.ltos.bean.User;

@Data
@Builder
@ToString
public class UserInfo {

    private Long id;
    private String email;
    private String name;
    private Boolean confirmedRegistration;
    private String unityPersistentIdentity;
    private boolean operator;

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
        UserInfoBuilder builder = UserInfo.builder()
            .id(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .confirmedRegistration(user.getConfirmedRegistration())
            .unityPersistentIdentity(user.getUnityPersistentIdentity());
        /*
         * TODO fix role assignment below
         */
        if(user!=null && user.getRoles()!=null){
            for(Role role: user.getRoles()) {
                if (role.getName().equals("provider") || role.getName().equals("admin")) {
                    builder.operator(true);
                }
            }
        }
        return builder.build();
    }

}
