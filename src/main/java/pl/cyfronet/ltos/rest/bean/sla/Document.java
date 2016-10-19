package pl.cyfronet.ltos.rest.bean.sla;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

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
public class Document {
    private String author;
    private String id;
    private HashMap<String, Object> metrics;
    private String name;
    private String site;
}

