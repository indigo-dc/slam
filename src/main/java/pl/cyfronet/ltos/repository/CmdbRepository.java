package pl.cyfronet.ltos.repository;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Component;

import pl.cyfronet.bazaar.engine.extension.metric.SiteSelectMetric;

@Component
public class CmdbRepository {
    @Value("${cmdb.url}")
    private String cmdbUrl;

    @Autowired
    private OAuth2RestOperations restTemplate;

    public JSONObject get(String type, String fieldName, String fieldValue) {
        return new JSONObject(restTemplate
                .getForObject(cmdbUrl + "/cmdb/" + type + "/filters/" + fieldName + "/" + fieldValue, Map.class));
    }

    public JSONObject get(String type) {
        return new JSONObject(restTemplate.getForObject(cmdbUrl + "/cmdb/" + type + "/list", Map.class));
    }

    @PostConstruct
    private void injectIntoEngine() {
        SiteSelectMetric.cmdbRepository = this;
    }
}
