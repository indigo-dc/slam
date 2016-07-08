package pl.cyfronet.ltos.rest;

import lombok.*;

/**
 * Created by km on 08.07.16.
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRest {
    private String name;
    private String fullname;
    private String country;
    private String email;
}
