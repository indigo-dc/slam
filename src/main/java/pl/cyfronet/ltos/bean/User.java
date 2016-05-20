package pl.cyfronet.ltos.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import pl.cyfronet.ltos.beansecurity.OwnedResource;
import lombok.Data;

@Data
@Entity
public class User implements OwnedResource {
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
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
	@Override
	
	public String getOwnerName() {
		return name;
	}

}
