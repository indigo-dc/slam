package pl.cyfronet.ltos.bean;

import java.util.List;

import javax.persistence.Column;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(unique=true)
    private String name;
    
    @ManyToMany(mappedBy="roles")
    private List<User> users;
    
    @ManyToMany(mappedBy="roles")
    private List<Team> teams;
    
}
