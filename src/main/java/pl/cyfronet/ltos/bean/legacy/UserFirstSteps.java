package pl.cyfronet.ltos.bean.legacy;

import lombok.Data;

import org.springframework.stereotype.Component;

@Data
@Component
public class UserFirstSteps {

    private boolean hasAffiliation;
    private boolean hasResource;
    private boolean hasScienceGateway;

}
