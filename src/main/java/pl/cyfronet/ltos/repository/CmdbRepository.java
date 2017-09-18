package pl.cyfronet.ltos.repository;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Component;
import pl.cyfronet.bazaar.engine.extension.metric.SiteSelectMetric;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class CmdbRepository {
    @Value("${cmdb.url}")
    private String cmdbUrl;

    @Value("${cmdb.url.prefix:/cmdb}")
    private String prefix;

    @Autowired
    private OAuth2RestOperations restTemplate;

    public JSONObject get(String type, String fieldName, String fieldValue) {
        return new JSONObject(restTemplate
                .getForObject(cmdbUrl + prefix + type + "/filters/" + fieldName + "/" + fieldValue, Map.class));
    }

    public JSONObject get(String type) {
        return new JSONObject(restTemplate.getForObject(cmdbUrl + prefix + type + "/list", Map.class));
    }

    public JSONObject getById(String type, String id) {
        return new JSONObject(restTemplate.getForObject(cmdbUrl + prefix + type + "/id/" + id, Map.class));
    }

    @PostConstruct
    private void injectIntoEngine() {
        SiteSelectMetric.cmdbRepository = this;
    }
}
