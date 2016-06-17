package pl.cyfronet.ltos.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

/**
 * @author bwilk
 *
 */
@Entity
@Data
public class Team {

    @Id
    @Column
    private long id;
    private String name;
    
    @ManyToMany(mappedBy="teams")
    private List<User> members;
    
}
