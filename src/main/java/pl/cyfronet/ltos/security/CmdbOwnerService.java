package pl.cyfronet.ltos.security;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import pl.cyfronet.ltos.repository.CmdbRepository;

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

    public Set<String> getOwnedProviders(String email) {
        if (isCacheInvalid()) {
            synchronized (this) {
                // If entering critical section after waiting on lock, the cache may already have
                // been refreshed. So check again.
                if (isCacheInvalid()) {
                    doRefresh();
                }
            }
        }
        return ownerEmailToProviders.getOrDefault(email, Collections.EMPTY_SET);
    }

    public void invalidateCache() {
        lastRefresh = 0L;
    }

    private boolean isCacheInvalid() {
        return System.currentTimeMillis() - lastRefresh > refreshInterval;
    }

    private void doRefresh() {
        try {
            try {
                Map<String, Set<String>> map = new HashMap<>();

                // expects a response in a shape of: { rows: [ { id: <id>, ... }, ... ] }
                JSONArray providersJSONArray = cmdbRepository.get("provider").getJSONArray("rows");
                List<String> providerIds = new ArrayList<>();
                for (int i = 0; i < providersJSONArray.length(); i++) {
                    providerIds.add(providersJSONArray.getJSONObject(i).getString("id"));
                }

                for (String providerId : providerIds) {
                    // expects { data: { owners: [ <email1>, <email2>, ... ], ... } }
                    // however, ignore entries with no owners field
                    JSONArray ownersJSONArray = cmdbRepository.getById("provider", providerId)
                            .getJSONObject("data")
                            .optJSONArray("owners");
                    if (ownersJSONArray != null && ownersJSONArray.length() > 0) {
                        for (int i = 0; i < ownersJSONArray.length(); i++) {
                            String email = ownersJSONArray.getString(i);
                            Set<String> providerIdsSet = map.getOrDefault(email, new HashSet<>());
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
