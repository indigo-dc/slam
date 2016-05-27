package pl.cyfronet.ltos.bean;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude="affiliations")
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
    @OneToMany(mappedBy="owner")
    private List<Affiliation> affiliations;

}
