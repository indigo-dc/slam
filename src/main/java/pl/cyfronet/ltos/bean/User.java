package pl.cyfronet.ltos.bean;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author bwilk
 *
 */
@Entity
@Data
@ToString(exclude = "affiliations")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String country;
    private String email;
    private String researchGate;
    private String linkedln;
    private boolean isPolicyAccepted;
    private Boolean confirmedRegistration;
    private String unityPersistentIdentity;
    private boolean hasActiveSla;
    @OneToMany(mappedBy = "owner")
    private List<Affiliation> affiliations;

}
