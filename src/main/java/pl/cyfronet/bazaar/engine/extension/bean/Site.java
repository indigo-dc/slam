package pl.cyfronet.bazaar.engine.extension.bean;

import lombok.Builder;
import lombok.Data;

/**
 * Created by mszostak on 17.05.17.
 */
@Data
@Builder
public class Site {
    private String name;
    private String id;
}
