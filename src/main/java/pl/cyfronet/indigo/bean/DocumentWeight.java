package pl.cyfronet.indigo.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * @author bwilk
 *
 */
@Entity
@Data
@IdClass(DocumentWeightPk.class)
public class DocumentWeight {

    @Id
    @ManyToOne
    @JsonIgnore
    private User user;

    @Id
    private String document;

    private Integer weight;

}
