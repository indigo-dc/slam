package pl.cyfronet.indigo.repository.onedata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Space {
    @JsonProperty("spaceId")
    public String spaceId;

    @JsonProperty("name")
    public String name;
}
