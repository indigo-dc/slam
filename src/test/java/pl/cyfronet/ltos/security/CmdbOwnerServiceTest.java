package pl.cyfronet.ltos.security;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Test;
import pl.cyfronet.ltos.repository.CmdbRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class CmdbOwnerServiceTest {

    @Test
    public void shouldReturnProviderSetForEmail() {
        CmdbRepository cmdbRepository = mockCmdbRepository();
        CmdbOwnerService service = new CmdbOwnerService(
                cmdbRepository,
                1000); // something large, so that the cache won't expire

        Set<String> providers1 = service.getOwnedProviders("1@owner");
        assertThat(providers1, Matchers.containsInAnyOrder("provider1", "provider2", "provider3"));

        Set<String> providers2 = service.getOwnedProviders("2@owner");
        assertThat(providers2, Matchers.containsInAnyOrder("provider1", "provider3"));

        Set<String> providers3 = service.getOwnedProviders("3@owner");
        assertThat(providers3, Matchers.containsInAnyOrder("provider2", "provider3"));
    }

    @Test
    public void shouldCacheResponses() {
        CmdbRepository cmdbRepository = mockCmdbRepository();
        CmdbOwnerService service = new CmdbOwnerService(
                cmdbRepository,
                1000); // something large, so that the cache won't expire

        service.getOwnedProviders("1@owner");
        service.getOwnedProviders("3@owner");
        service.getOwnedProviders("2@owner");
        service.getOwnedProviders("1@owner");

        verify(cmdbRepository, times(1)).get("provider");
        verify(cmdbRepository, times(1)).getById("provider", "provider1");
        verify(cmdbRepository, times(1)).getById("provider", "provider2");
        verify(cmdbRepository, times(1)).getById("provider", "provider3");
    }

    @Test
    public void shouldReloadWhenNeeded() {
        CmdbRepository cmdbRepository = mockCmdbRepository();
        CmdbOwnerService service = new CmdbOwnerService(
                cmdbRepository,
                1000); // something large, so that the cache won't expire

        service.getOwnedProviders("1@owner");
        service.invalidateCache();

        Set<String> providers1 = service.getOwnedProviders("1@owner");
        assertThat(providers1, Matchers.containsInAnyOrder("provider1", "provider3"));

        Set<String> providers2 = service.getOwnedProviders("2@owner");
        assertThat(providers2, Matchers.containsInAnyOrder("provider1", "provider2", "provider3"));

        verify(cmdbRepository, times(2)).get("provider");
        verify(cmdbRepository, times(2)).getById("provider", "provider1");
        verify(cmdbRepository, times(2)).getById("provider", "provider2");
        verify(cmdbRepository, times(2)).getById("provider", "provider3");
    }

    private CmdbRepository mockCmdbRepository() {
        CmdbRepository cmdbRepository = mock(CmdbRepository.class);
        String repoProvidersReturn = "{\"rows\":[{\"id\":\"provider1\"},{\"id\":\"provider2\"},{\"id\":\"provider3\"}]}";
        Map<String, JSONObject[]> providerByIdReturns = new HashMap<>();
        providerByIdReturns.put("provider1", new JSONObject[]{
                new JSONObject("{\"data\":{\"owners\":[\"1@owner\",\"2@owner\"]}}")
        });
        providerByIdReturns.put("provider2", new JSONObject[]{
                new JSONObject("{\"data\":{\"owners\":[\"1@owner\",\"3@owner\"]}}"),
                new JSONObject("{\"data\":{\"owners\":[\"2@owner\",\"3@owner\"]}}")
        });
        providerByIdReturns.put("provider3", new JSONObject[]{
                new JSONObject("{\"data\":{\"owners\":[\"1@owner\",\"2@owner\",\"3@owner\"]}}")
        });

        when(cmdbRepository.get("provider")).thenReturn(new JSONObject(repoProvidersReturn));
        when(cmdbRepository.getById("provider", "provider1")).thenReturn(
                new JSONObject("{\"data\":{\"owners\":[\"1@owner\",\"2@owner\"]}}")
        );
        when(cmdbRepository.getById("provider", "provider2")).thenReturn(
                new JSONObject("{\"data\":{\"owners\":[\"1@owner\",\"3@owner\"]}}"),
                new JSONObject("{\"data\":{\"owners\":[\"2@owner\",\"3@owner\"]}}")
        );
        when(cmdbRepository.getById("provider", "provider3")).thenReturn(
                new JSONObject("{\"data\":{\"owners\":[\"1@owner\",\"2@owner\",\"3@owner\"]}}")
        );
        return cmdbRepository;
    }
}
