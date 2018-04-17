package pl.cyfronet.indigo.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author bwilk
 */
@Entity
@Data
@ToString(exclude = {"affiliations", "teams", "roles", "documents"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String country;

    private String organisationName;

    @Column(unique = true)
    private String email;
    private String researchGate;
    private String linkedln;
    private boolean isPolicyAccepted;
    private Boolean confirmedRegistration;
    private String unityPersistentIdentity;
    private boolean hasActiveSla;

    @JsonIgnore
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Affiliation> affiliations;

    @JsonIgnore
    @ManyToMany(mappedBy = "members")
    private List<Team> teams;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    @OrderBy("weight")
    private List<DocumentWeight> documents;

    @JsonIgnore
    @ManyToMany
    private List<Role> roles;

    public boolean hasRole(String role) {
        for (Role _role : getRoles()) {
            if (_role.getName().equals(role)) {
                return true;
            }
        }
        return false;
    }
}
