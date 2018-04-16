package pl.cyfronet.indigo.engine.extension.constraint.action.definition;

import com.agreemount.slaneg.annotation.HandleWithImplementation;
import com.agreemount.slaneg.constraint.action.definition.ActionConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.cyfronet.indigo.engine.extension.constraint.action.impl.HasAnyOfMetricsImpl;

import java.util.List;

/**
 * Created by chomik on 27.01.16.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@HandleWithImplementation(runner = HasAnyOfMetricsImpl.class)
public class HasAnyOfMetrics extends ActionConstraint {
    private String documentAlias = "BASE";
    private List<String> metrics;

}