package pl.cyfronet.ltos.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

@Data
@Entity
public class User {
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    @Column(unique = true)
    private String login;
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
