package pl.cyfronet.ltos.rest.bean.preferences;

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
    private String customer= "indigo-dc";
    private List<Priority> priority;
}
