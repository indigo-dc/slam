package pl.cyfronet.ltos.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Affiliation {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String country;
    private String institution;
    private String department;
    private String webPage;
    private String supervisorName;
    private String supervisorEmail;
    private String status;
    private Date lastUpdateDate;
    private String userEmail;

}	
