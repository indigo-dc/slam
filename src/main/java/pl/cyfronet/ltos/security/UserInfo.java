package pl.cyfronet.ltos.security;

        import lombok.AllArgsConstructor;
        import lombok.Builder;
        import lombok.Data;
        import lombok.ToString;
        import org.codehaus.jackson.annotate.JsonCreator;
        import org.codehaus.jackson.annotate.JsonProperty;
        import pl.cyfronet.ltos.bean.Role;
        import pl.cyfronet.ltos.bean.User;

@Data
@Builder
@AllArgsConstructor
@ToString
public class UserInfo {

    private Long id;
    private String email;

    private String name;

    private Boolean confirmedRegistration;

    private String unityPersistentIdentity;
    private boolean operator;

    public UserInfo() {
    }

    @JsonCreator
    public UserInfo(@JsonProperty("email")String email,
                    @JsonProperty("name")String name,
                    @JsonProperty("email_verified")Boolean confirmedRegistration,
                    @JsonProperty("persistent")String persistent
    ) {
        this.email = email;
        this.name = name;
        this.confirmedRegistration = confirmedRegistration;
        this.unityPersistentIdentity = persistent;
    }

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
        for(Role role: user.getRoles()) {
            if (role.getName().equals("operator") || role.getName().equals("admin")) {
                builder.operator(true);
            }
        }
        return builder.build() ;
    }

}
