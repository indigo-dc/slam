package pl.cyfronet.indigo.engine.extension.constraint.query.definition;

import com.agreemount.slaneg.constraint.query.definition.QueryConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class IsAdministratorInRelatedSite extends QueryConstraint {
    private String sth;
    private String siteMetricId;
}
