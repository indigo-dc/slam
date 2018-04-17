package pl.cyfronet.indigo.rest.bean.sla;

import lombok.*;

import java.util.List;

/**
 * Created by km on 11.07.16.
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    private String type;
    private String service_id;
    private List<Target> targets;
}
