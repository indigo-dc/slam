package pl.cyfronet.ltos.security;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class UserInfo {

    private String email;
    private String name;
    private Boolean confirmedRegistration;
    private String unityPersistentIdentity;
  
}
