package pl.cyfronet.indigo.rest.bean.preferences;

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
public class Preferences {
    private String customer;
    private List<Preference> preferences;
    private String id;
}
