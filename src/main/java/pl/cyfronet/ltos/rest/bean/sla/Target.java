package pl.cyfronet.ltos.rest.bean.sla;

import lombok.*;

import java.util.Map;

/**
 * Created by km on 11.07.16.
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Target {
    private String type;
    private String unit;
    Map<String,Object> restrictions;
}
