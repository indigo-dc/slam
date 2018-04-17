package pl.cyfronet.indigo.rest.bean.sla;

import lombok.*;

import java.util.HashMap;

/**
 * Created by km on 11.07.16.
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    private String author;
    private String id;
    private HashMap<String, Object> metrics;
    private String name;
    private String site;
    private String siteName;
}

