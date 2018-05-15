package pl.cyfronet.indigo.security;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import pl.cyfronet.indigo.repository.CmdbRepository;

import java.util.*;

@Service
@Slf4j
public class CmdbOwnerService {

    private long lastRefresh = 0L;

    private Map<String, Set<String>> ownerEmailToProviders = null;

    private final long refreshInterval;

    private final CmdbRepository cmdbRepository;

    @Autowired
    public CmdbOwnerService(
            CmdbRepository cmdbRepository,
            @Value("${cmdb.owner.refreshInterval:5000}") long refreshInterval) {
        this.cmdbRepository = cmdbRepository;
        this.refreshInterval = refreshInterval;
    }

    public synchronized Set<String> getOwnedProviders(String email) {
        if (isCacheInvalid()) {
            doRefresh();
        }
        return ownerEmailToProviders.getOrDefault(email, Collections.EMPTY_SET);
    }

    public synchronized void invalidateCache() {
        lastRefresh = 0L;
    }

    private boolean isCacheInvalid() {
        return System.currentTimeMillis() - lastRefresh > refreshInterval;
    }

    private void doRefresh() {
        try {
            try {
                Map<String, Set<String>> map = new HashMap<>();

                // expects a response in a shape of: { rows: [ { id: <id>, doc: { data: { owners: [ <email1>, <email2>, ... ], ... } }, ... }, ... ] }
                JSONArray providersJSONArray = cmdbRepository.get("provider", "include_docs=true").getJSONArray("rows");
                for (int i = 0; i < providersJSONArray.length(); i++) {
                    JSONObject row = providersJSONArray.getJSONObject(i);
                    JSONObject doc = row.getJSONObject("doc");
                    JSONArray owners = doc.getJSONObject("data").optJSONArray("owners");
                    String providerId = row.getString("id");
//                    String providerName = doc.getJSONObject("data").getString("name");
                    if (owners != null && owners.length() > 0) {
                        for (int j = 0; j < owners.length(); j++) {
                            String email = owners.getString(j);
                            Set<String> providerIdsSet = map.getOrDefault(email, new HashSet<>());
//                            providerIdsSet.add(providerName);
                            providerIdsSet.add(providerId);
                            map.put(email, providerIdsSet);
                        }
                    }
                }

                ownerEmailToProviders = map;
            } catch (Exception e) {
                // ensure a clean map here to avoid it being in some dirty state
                ownerEmailToProviders = Collections.EMPTY_MAP;
                throw e;
            }
        // just log, handling the permissions in other way wouldn't make sense anyway
        // maybe in the next refreshInterval millis everything will fix itself
        } catch (RestClientException e) {
            log.warn("Couldn't execute request to CMDB", e);
        } catch (JSONException e) {
            log.warn("Malformed response from CMDB", e);
        } catch (Exception e) {
            log.warn("Unexpected exception", e);
        } finally {
            lastRefresh = System.currentTimeMillis();
        }
    }
}
