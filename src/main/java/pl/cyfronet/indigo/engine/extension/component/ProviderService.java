package pl.cyfronet.indigo.engine.extension.component;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import pl.cyfronet.indigo.repository.CmdbRepository;

import java.util.HashMap;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ProviderService {

    @Autowired
    private CmdbRepository cmdbRepository;

    private HashMap<String, String> providerIds = null;

    public HashMap<String, String> getProviderIds() {
        if(providerIds != null)
            return providerIds;

        providerIds = new HashMap<>();

        try {
            JSONArray sites = cmdbRepository.get("service").getJSONArray("rows");
            for(int i = 0; i < sites.length(); ++i ) {
                JSONObject site = sites.getJSONObject(i);
                this.providerIds.put(site.getString("id"),
                        site.getJSONObject("value").has("provider_id") ? site.getJSONObject("value").getString("provider_id") : "");
            }
            return this.providerIds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public String getProviderId(String siteId) {
        if(siteId == null)
            return null;
        return getProviderIds().get(siteId);
    }
}