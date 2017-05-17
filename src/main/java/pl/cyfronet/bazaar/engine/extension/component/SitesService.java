package pl.cyfronet.bazaar.engine.extension.component;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.cyfronet.bazaar.engine.extension.bean.Site;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mszostak on 06.04.17.
 */

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SitesService {

    @Value("${cmdb.url}")
    private String cmdbUrl;


    private HashMap<String, Site> sites = null;

    public HashMap<String, Site> getSites() {
        if(sites != null)
            return sites;

        sites = new HashMap<>();

        try {
            JSONArray sites = Unirest.get(cmdbUrl+"/cmdb/service/list").asJson().getBody().getObject().getJSONArray("rows");
            for(int i = 0; i < sites.length(); ++i ) {
                JSONObject site = sites.getJSONObject(i);
                this.sites.put(site.getString("id"),
                               Site.builder().id(site.getString("id"))
                               .name(site.getJSONObject("value").getString("sitename"))
                               .build());
            }
            return this.sites;
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSiteName(String siteId) {
        if(siteId == null)
            return null;
        return getSites().get(siteId).getName();
    }
}
