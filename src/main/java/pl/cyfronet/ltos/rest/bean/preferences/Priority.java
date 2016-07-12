package pl.cyfronet.ltos.rest.bean.preferences;

import lombok.*;

/**
 * Created by km on 11.07.16.
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Priority {
    private String sla_id;
    private String service_id;
    private Double weight;
}
