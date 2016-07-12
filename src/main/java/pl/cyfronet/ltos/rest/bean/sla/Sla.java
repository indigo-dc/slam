package pl.cyfronet.ltos.rest.bean.sla;

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
public class Sla {
    private String customer;
    private String provider;
    private String start_date;
    private String end_date;
    private List<Service> services;
    private String id;
}
