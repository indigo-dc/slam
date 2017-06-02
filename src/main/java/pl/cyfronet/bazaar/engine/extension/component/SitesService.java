package pl.cyfronet.bazaar.engine.extension.component;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import pl.cyfronet.bazaar.engine.extension.bean.Site;
import pl.cyfronet.ltos.repository.CmdbRepository;

/**
 * Created by mszostak on 06.04.17.
 */

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SitesService {

    @Autowired
    private CmdbRepository cmdbRepository;

    private HashMap<String, Site> sites = null;

    public HashMap<String, Site> getSites() {
        if(sites != null)
            return sites;

        sites = new HashMap<>();

        try {
            JSONArray sites = cmdbRepository.get("service").getJSONArray("rows");
            for(int i = 0; i < sites.length(); ++i ) {
                JSONObject site = sites.getJSONObject(i);
                this.sites.put(site.getString("id"),
                               Site.builder().id(site.getString("id"))
                               .name(site.getJSONObject("value").has("sitename") ? site.getJSONObject("value").getString("sitename") : "")
                               .build());
            }
            return this.sites;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public String getSiteName(String siteId) {
        if(siteId == null)
            return null;
        return getSites().get(siteId).getName();
    }
}
