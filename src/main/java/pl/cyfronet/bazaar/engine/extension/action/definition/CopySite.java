package pl.cyfronet.bazaar.engine.extension.action.definition;

import com.agreemount.slaneg.annotation.HandleWithImplementation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Paweł Szepieniec pawel.szepieniec@gmail.com on 19.10.2016.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@HandleWithImplementation(runner = CopySiteImpl.class)
public class CopySite extends Action {

    private String fromAlias;
    private String toAlias;

}
