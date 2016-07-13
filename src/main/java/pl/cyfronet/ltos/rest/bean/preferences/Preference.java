package pl.cyfronet.ltos.rest.bean.preferences;

import lombok.*;

import java.util.List;

/**
 * Created by km on 13.07.16.
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Preference {
    private String service_type;
    private List<Priority> priority;
}
