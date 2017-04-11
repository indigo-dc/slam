package pl.cyfronet.ltos.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author bwilk
 *
 */
@Entity
//@Data
@IdClass(DocumentWeightPk.class)
public class DocumentWeight {

    @Id
    @OneToOne(targetEntity = User.class)
    protected User user;
    @Id
    protected String document;

    private Integer weight;

}
