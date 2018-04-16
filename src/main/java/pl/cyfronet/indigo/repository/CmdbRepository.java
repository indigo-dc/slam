package pl.cyfronet.indigo.repository;

import lombok.extern.log4j.Log4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Component;
import pl.cyfronet.engine.extension.constraint.action.impl.IsPublicServiceImpl;
import pl.cyfronet.engine.extension.metric.SiteSelectMetric;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
@Log4j
public class CmdbRepository {
    @Value("${cmdb.url}")
    private String cmdbUrl;

    @Value("${cmdb.url.prefix:/cmdb}")
    private String prefix;

    @Autowired
    private OAuth2RestOperations restTemplate;

    public JSONObject get(String type, String fieldName, String fieldValue) {
        String url = cmdbUrl + prefix + "/"+ type + "/filters/" + fieldName + "/" + fieldValue;
        log.debug("Calling " + url);
        return new JSONObject(restTemplate
                .getForObject(url, Map.class));
    }

    public JSONObject get(String type) {
        return get(type, null);
    }

    public JSONObject get(String type, String params) {
        String url = cmdbUrl + prefix + "/"+ type + "/list";
        if (params != null) {
            url = url + "?"+params;
        }
        log.debug("Calling " + url);
        return new JSONObject(restTemplate.getForObject(url, Map.class));
    }

    public JSONObject getById(String type, String id) {
        String url = cmdbUrl + prefix + "/"+ type + "/id/" + id;
        log.debug("Calling " + url);
        return new JSONObject(restTemplate.getForObject(cmdbUrl + prefix + "/" + type + "/id/" + id, Map.class));
    }

    @PostConstruct
    private void injectIntoEngine() {
        SiteSelectMetric.cmdbRepository = this;
        IsPublicServiceImpl.cmdbRepository = this;
    }
}
