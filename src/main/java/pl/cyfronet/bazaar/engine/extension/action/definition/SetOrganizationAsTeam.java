package pl.cyfronet.bazaar.engine.extension.action.definition;

import com.agreemount.slaneg.action.definition.Action;
import com.agreemount.slaneg.annotation.HandleWithImplementation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.cyfronet.bazaar.engine.extension.action.impl.SetOrganizationAsTeamImpl;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@HandleWithImplementation(runner = SetOrganizationAsTeamImpl.class)
public class SetOrganizationAsTeam extends Action {
    private String documentAlias;
}