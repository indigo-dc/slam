package pl.cyfronet.indigo.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

/**
 * @author bwilk
 *
 */
@Entity
@Data
@ToString
public class Affiliation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String type;
    private String name;
    private String acronym;
    private String department;
    private String address;
    private String zipCode;
    private String city;
    private String country;
    private String primaryPhone;
    private String secondaryPhone;
    private String webPage;
    private Date lastUpdateDate;
    
    private String status;
    
    /*
     * TODO: EAGER causes N+1 query problem but spring-data-rest does not cope
     * with this automatically left as it is until we investigate performance
     */
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User owner;

}
