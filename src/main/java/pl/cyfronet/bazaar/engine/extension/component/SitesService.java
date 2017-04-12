package pl.cyfronet.bazaar.engine.extension.component;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by mszostak on 06.04.17.
 */

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SitesService {

    @Value("${cmdb.url}")
    private String cmdbUrl;

//    private

    public String getSites() {
        try {
            return Unirest.get(cmdbUrl+"/cmdb/service/list").asString().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
    }
}
