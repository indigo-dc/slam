package pl.cyfronet.ltos.bean;

import javax.persistence.Embeddable;
import javax.persistence.IdClass;
import java.io.Serializable;

/**
 * Created by mszostak on 11.04.17.
 */

public class DocumentWeightPk implements Serializable {
    protected User user;
    protected String document;

    public DocumentWeightPk() {}

    public DocumentWeightPk(User user, String document) {
        this.user = user;
        this.document = document;
    }
    // equals, hashCode
}

