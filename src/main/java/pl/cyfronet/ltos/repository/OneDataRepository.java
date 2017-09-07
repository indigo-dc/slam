package pl.cyfronet.ltos.repository;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import pl.cyfronet.bazaar.engine.extension.metric.SpaceMetric;
import pl.cyfronet.ltos.repository.onedata.Space;

@Component
public class OneDataRepository {
    @Value("${onedata.url}")
    private String oneDataUrl;

    @Value("${onedata.verification:false}")
    private boolean oneDataVerification;

    @Autowired
    private OAuth2RestOperations oAuth2RestTemplate;

    private RestTemplate restTemplate;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class SpacesResponse {
        @JsonProperty("spaces")
        public List<String> spaces;

    }

    public OneDataRepository() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        if (oneDataVerification) {
            restTemplate = new RestTemplate();
        } else {
            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();

            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(csf)
                    .build();

            HttpComponentsClientHttpRequestFactory requestFactory =
                    new HttpComponentsClientHttpRequestFactory();

            requestFactory.setHttpClient(httpClient);

            restTemplate = new RestTemplate(requestFactory);
        }
    }

    public List<Space> getUserSpaces() {
        return restTemplate.exchange(oneDataUrl + "/api/v3/onezone/user/spaces", HttpMethod.GET, getEntity(), SpacesResponse.class)
                .getBody().spaces.stream().map(spaceId -> getSpaceDetails(spaceId)).collect(Collectors.toList());
    }

    private Space getSpaceDetails(String spaceId) {
        return restTemplate.exchange(oneDataUrl + "/api/v3/onezone/user/spaces/" + spaceId,
                HttpMethod.GET, getEntity(), Space.class).getBody();
    }

    private HttpEntity<String> getEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", "indigo:" + oAuth2RestTemplate.getAccessToken().getValue());

        return new HttpEntity<>(headers);
    }

    @PostConstruct
    private void injectIntoEngine() {
        SpaceMetric.oneDataClient = this;
    }
}
