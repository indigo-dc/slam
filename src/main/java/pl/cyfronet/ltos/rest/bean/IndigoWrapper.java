package pl.cyfronet.ltos.rest.bean;

import lombok.*;
import pl.cyfronet.ltos.rest.bean.preferences.Preferences;
import pl.cyfronet.ltos.rest.bean.sla.Sla;

import java.util.List;

/**
 * Created by km on 11.07.16.
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndigoWrapper {
    private List<Preferences> preferences;
    private List<Sla> sla;
}
