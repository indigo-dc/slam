package pl.cyfronet.ltos.bean;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

/**
 * @author bwilk
 *
 */
@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    private String name;
    
    @ManyToMany(mappedBy="userRoles")
    private List<User> users;
    
    @ManyToMany(mappedBy="teamRoles")
    private List<Team> teams;
    
}
