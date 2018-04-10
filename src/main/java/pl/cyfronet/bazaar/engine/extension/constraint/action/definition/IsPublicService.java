package pl.cyfronet.bazaar.engine.extension.constraint.action.definition;

import com.agreemount.slaneg.annotation.HandleWithImplementation;
import com.agreemount.slaneg.constraint.action.definition.ActionConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.cyfronet.bazaar.engine.extension.constraint.action.impl.IsPublicServiceImpl;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@HandleWithImplementation(runner = IsPublicServiceImpl.class)
public class IsPublicService extends ActionConstraint {
    private String documentAlias = "BASE";
    private String metricId;
}